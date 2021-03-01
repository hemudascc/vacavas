package net.mycomp.oredoo.kuwait;


public class OredooKuwaitCGToken {

	private int tokenId;
	
	public OredooKuwaitCGToken(long time,int tokenId,int campaignId){
		this.tokenId=tokenId;
	}
	
	public Integer getCGToken(){
		 return  tokenId;
	}
	
	public OredooKuwaitCGToken(Integer tokenId){
			this.tokenId=tokenId;
	}
	
	public OredooKuwaitCGToken(String tokenId){
		try{
		this.tokenId=Integer.parseInt(tokenId);
		}catch(Exception ex){
			
		}
}


	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
}
