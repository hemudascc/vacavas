package net.mycomp.tpay;

import java.util.List;
import net.util.MUtility;
import net.persist.bean.LiveReport;
import java.sql.Timestamp;
import net.util.MData;
import net.persist.bean.VWServiceCampaignDetail;
import java.util.Date;
import net.persist.bean.SubscriberReg;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import javax.jms.ObjectMessage;
import net.process.bean.CGToken;
import javax.jms.Message;
import net.jpa.repository.JPASubscriberReg;
import net.common.service.SubscriberRegService;
import net.common.service.RedisCacheService;
import net.common.service.LiveReportFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import net.common.service.IDaoService;
import org.apache.log4j.Logger;
import javax.jms.MessageListener;

public class JMSTpayNotificationListener implements MessageListener
{
    private static final Logger logger;
    @Autowired
    private IDaoService daoService;
    @Autowired
    private LiveReportFactoryService liveReportFactoryService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private SubscriberRegService subscriberRegService;
    @Autowired
    private JPASubscriberReg jpaSubscriberReg;
    
    static {
        logger = Logger.getLogger((Class)JMSTpayNotificationListener.class);
    }
    
    public void onMessage(final Message message) {
        TpayNotification tpayNotification = null;
        LiveReport liveReport = null;
        boolean update = false;
        final long time = System.currentTimeMillis();
        CGToken cgToken = new CGToken("");
        TpayServiceConfig tpayServiceConfig = null;
        boolean sameDaysub = false;
        try {
            final ObjectMessage objectMessage = (ObjectMessage)message;
            tpayNotification = (TpayNotification)objectMessage.getObject();
            JMSTpayNotificationListener.logger.info((Object)("tpayNotification::::: " + tpayNotification));
            final List<SubscriberReg> subscriberRegs = (List<SubscriberReg>)this.jpaSubscriberReg.findSubscriberRegByParam1(tpayNotification.getSubscriptionContractId());
            if (Objects.nonNull(subscriberRegs) && subscriberRegs.size() > 0) {
                sameDaysub = LocalDate.now().equals(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(subscriberRegs.get(0).getRegDate())));
                cgToken = new CGToken(subscriberRegs.get(0).getParam3());
            }
            else {
                cgToken = new CGToken("-1c-1c1");
            }
            final VWServiceCampaignDetail vwServiceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
            JMSTpayNotificationListener.logger.info((Object)("view " + vwServiceCampaignDetail));
            if (vwServiceCampaignDetail != null) {
                tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig.get(vwServiceCampaignDetail.getServiceId());
            }
            if (tpayServiceConfig == null) {
                tpayServiceConfig = TpayConstant.mapServiceIdToTpayServiceConfig.get(1);
            }
            JMSTpayNotificationListener.logger.info((Object)("tpay config " + tpayServiceConfig));
            liveReport = new LiveReport((int)tpayServiceConfig.getOperatorId(), new Timestamp(System.currentTimeMillis()), Integer.valueOf(cgToken.getCampaignId()), (int)tpayServiceConfig.getServiceId(), 0);
            liveReport.setProductId(tpayServiceConfig.getProductId());
            liveReport.setTokenId(Integer.valueOf(cgToken.getTokenId()));
            liveReport.setToken(cgToken.getCGToken());
            liveReport.setMsisdn(tpayNotification.getMsisdn());
            liveReport.setCircleId(0);
            tpayNotification.setToken(cgToken.getCGToken());
            tpayNotification.setTokenId(Integer.valueOf(cgToken.getTokenId()));
            tpayNotification.setAction(this.findAction(tpayNotification, liveReport, tpayServiceConfig, sameDaysub));
            JMSTpayNotificationListener.logger.info((Object)("tpay action : " + tpayNotification.getAction()));
            final double priceAmount = MUtility.toDouble(tpayNotification.getAmount(), -1.0);
            if (tpayNotification.getAction().equalsIgnoreCase("RECYCLED_SUBCRIBER")) {
                liveReport.setParam1(tpayNotification.getSubscriptionContractId());
                liveReport.setParam2(tpayServiceConfig.getOperatorCode());
                liveReport.setAction("GRACE");
                liveReport.setGraceConversionCount(1);
            }
            else if (tpayNotification.getAction().equalsIgnoreCase("ACT")) {
                liveReport.setAction("ACT");
                liveReport.setConversionCount(1);
                liveReport.setAmount(Double.valueOf((priceAmount > 0.0) ? priceAmount : Double.valueOf(tpayServiceConfig.getPrice())));
                liveReport.setNoOfDays((int)tpayServiceConfig.getValidity());
                liveReport.setParam1(tpayNotification.getSubscriptionContractId());
                liveReport.setParam2(tpayServiceConfig.getOperatorCode());
                tpayNotification.setValidity(Integer.valueOf(liveReport.getNoOfDays()));
                tpayNotification.setAmount(liveReport.getAmount().toString());
            }
            else if (tpayNotification.getAction().equalsIgnoreCase("RENEW")) {
                liveReport.setAction("RENEW");
                liveReport.setRenewalCount(1);
                liveReport.setRenewalAmount(Double.valueOf((priceAmount > 0.0) ? priceAmount : Double.valueOf(tpayServiceConfig.getPrice())));
                liveReport.setNoOfDays((int)tpayServiceConfig.getValidity());
                liveReport.setParam1(tpayNotification.getSubscriptionContractId());
                liveReport.setParam2(tpayServiceConfig.getOperatorCode());
                tpayNotification.setValidity(Integer.valueOf(liveReport.getNoOfDays()));
                tpayNotification.setAmount(liveReport.getRenewalAmount().toString());
            }
            else if (tpayNotification.getAction().equalsIgnoreCase("DCT")) {
                liveReport.setAction("DCT");
                liveReport.setDctCount(1);
            }
        }
        catch (Exception e) {
            JMSTpayNotificationListener.logger.error((Object)"onMessage::::: ", (Throwable)e);
            return;
        }
        finally {
            Label_1052: {
                try {
                    if (liveReport.getAction() != null) {
                        this.liveReportFactoryService.process(liveReport);
                    }
                    tpayNotification.setSendToAdnetwork(Boolean.valueOf(liveReport.getSendConversionCount() > 0));
                }
                catch (Exception ex) {
                    JMSTpayNotificationListener.logger.error((Object)(" fianlly liveReport:: " + liveReport + ", : tpayNotification:: " + tpayNotification));
                    JMSTpayNotificationListener.logger.error((Object)"onMessage::::::::::finally ", (Throwable)ex);
                    update = this.daoService.saveObject((Object)tpayNotification);
                    break Label_1052;
                }
                finally {
                    update = this.daoService.saveObject((Object)tpayNotification);
                }
                update = this.daoService.saveObject((Object)tpayNotification);
            }
            JMSTpayNotificationListener.logger.info((Object)("onMessage::::::::::::::::: :: update::live report " + update + ", total time:: " + (System.currentTimeMillis() - time)));
        }
        Label_1222: {
            try {
                if (liveReport.getAction() != null) {
                    this.liveReportFactoryService.process(liveReport);
                }
                tpayNotification.setSendToAdnetwork(Boolean.valueOf(liveReport.getSendConversionCount() > 0));
            }
            catch (Exception ex) {
                JMSTpayNotificationListener.logger.error((Object)(" fianlly liveReport:: " + liveReport + ", : tpayNotification:: " + tpayNotification));
                JMSTpayNotificationListener.logger.error((Object)"onMessage::::::::::finally ", (Throwable)ex);
                break Label_1222;
            }
            finally {
                update = this.daoService.saveObject((Object)tpayNotification);
            }
            update = this.daoService.saveObject((Object)tpayNotification);
        }
        JMSTpayNotificationListener.logger.info((Object)("onMessage::::::::::::::::: :: update::live report " + update + ", total time:: " + (System.currentTimeMillis() - time)));
    }
    
    private String findAction(final TpayNotification tpayNotification, final LiveReport liveReport, final TpayServiceConfig tpayServiceConfig, final boolean sameDaysub) {
        boolean subscribed = false;
        if (tpayNotification.getProductId() != null) {
            subscribed = this.subscriberRegService.isSubscribedBySericeId(tpayNotification.getMsisdn(), (int)tpayServiceConfig.getProductId());
            JMSTpayNotificationListener.logger.info((Object)("subscribed = " + subscribed));
        }
        if ("SubscriptionChargingNotification".equalsIgnoreCase(tpayNotification.getTpayAction())) {
            JMSTpayNotificationListener.logger.info((Object)"inside SubscriptionChargingNotification");
            if (!subscribed || Objects.nonNull(this.redisCacheService.getObjectCacheValue("TPAY_TEMP_SUBSCRIBE" + tpayNotification.getMsisdn()))) {
                this.redisCacheService.removeObjectCacheValue("TPAY_TEMP_SUBSCRIBE" + tpayNotification.getMsisdn());
                if ("PaymentCompletedSuccessfully".equalsIgnoreCase(tpayNotification.getPaymentTransactionStatusCode())) {
                    JMSTpayNotificationListener.logger.info((Object)"inside PaymentCompletedSuccessfully");
                    liveReport.setAction("ACT");
                    liveReport.setConversionCount(1);
                    if (tpayNotification.getAmount() != null) {
                        liveReport.setAmount(Double.valueOf((Double.parseDouble(tpayNotification.getAmount()) > 0.0) ? Double.parseDouble(tpayNotification.getAmount()) : Double.parseDouble(tpayServiceConfig.getPrice())));
                    }
                    else {
                        liveReport.setAmount(Double.valueOf(Double.parseDouble(tpayServiceConfig.getPrice())));
                    }
                    liveReport.setNoOfDays((int)tpayServiceConfig.getValidity());
                    return "ACT";
                }
                if ("RetrailPayment".equals(tpayNotification.getBillingAction()) && sameDaysub) {
                    JMSTpayNotificationListener.logger.info((Object)"inside PaymentCompletedSuccessfully else");
                    liveReport.setAction("GRACE");
                    liveReport.setGraceConversionCount(1);
                    return "GRACE";
                }
            }
            else {
                if ("PaymentCompletedSuccessfully".equalsIgnoreCase(tpayNotification.getPaymentTransactionStatusCode()) && subscribed) {
                    JMSTpayNotificationListener.logger.info((Object)"inside PaymentCompletedSuccessfully");
                    liveReport.setAction("RENEW");
                    liveReport.setRenewalCount(1);
                    if (tpayNotification.getAmount() != null) {
                        liveReport.setRenewalAmount(Double.valueOf((Double.parseDouble(tpayNotification.getAmount()) > 0.0) ? Double.parseDouble(tpayNotification.getAmount()) : Double.parseDouble(tpayServiceConfig.getPrice())));
                    }
                    else {
                        liveReport.setRenewalAmount(Double.valueOf(Double.parseDouble(tpayServiceConfig.getPrice())));
                    }
                    liveReport.setNoOfDays((int)tpayServiceConfig.getValidity());
                    return "RENEW";
                }
                if ("RetrailPayment".equals(tpayNotification.getBillingAction()) && sameDaysub) {
                    JMSTpayNotificationListener.logger.info((Object)"inside PaymentCompletedSuccessfully else");
                    liveReport.setAction("GRACE");
                    liveReport.setGraceConversionCount(1);
                    return "GRACE";
                }
            }
        }
        else if ("SubscriptionContractStatusChanged".equalsIgnoreCase(tpayNotification.getTpayAction()) && "Canceled".equalsIgnoreCase(tpayNotification.getNotificationStatus())) {
            JMSTpayNotificationListener.logger.info((Object)"inside SubscriptionContractStatusChanged");
            liveReport.setAction("DCT");
            liveReport.setDctCount(1);
            return "DCT";
        }
        return "";
    }
}