package lt.lukasnakas.model.revolut.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.revolut.RevolutCard;

public class RevolutTransaction extends RevolutTransactionBase {

	private String type;

	@JsonProperty("updated_at")
	private String updatedAt;

	private RevolutMerchant merchant;
	private RevolutTransactionLegs[] legs;
	private RevolutCard card;

	public RevolutTransaction() {
	}

	public RevolutTransaction(String id, String state, String createdAt, String completedAt, String type,
							  String updatedAt, RevolutMerchant merchant, RevolutTransactionLegs[] legs,
							  RevolutCard card) {
		super(id, state, createdAt, completedAt);
		this.type = type;
		this.updatedAt = updatedAt;
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

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
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
