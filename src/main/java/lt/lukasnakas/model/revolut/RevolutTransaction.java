package lt.lukasnakas.model.revolut;


import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

public class RevolutTransaction extends Transaction {

	private String type;
	private String state;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("completed_at")
	private String completedAt;

	private RevolutMerchant merchant;
	private RevolutTransactionLegs[] legs;
	private RevolutCard card;

	public RevolutTransaction() {
	}

	public RevolutTransaction(String id, String type, String state,
							  String createdAt, String updatedAt, String completedAt,
							  RevolutMerchant merchant, RevolutTransactionLegs[] legs,
							  RevolutCard card) {
		super(id);
		this.type = type;
		this.state = state;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.completedAt = completedAt;
		this.merchant = merchant;
		this.legs = legs;
		this.card = card;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}

	public RevolutMerchant getMerchant() {
		return merchant;
	}

	public void setMerchant(RevolutMerchant merchant) {
		this.merchant = merchant;
	}

	public RevolutTransactionLegs[] getLegs() {
		return legs;
	}

	public void setLegs(RevolutTransactionLegs[] legs) {
		this.legs = legs;
	}

	public RevolutCard getCard() {
		return card;
	}

	public void setCard(RevolutCard card) {
		this.card = card;
	}
}
