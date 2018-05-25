/**
 * 
 */
package subhasys.api.restful.validation;

/**
 * @author subhasis
 *
 */
public class ValidationMessage {
	
	public boolean validationStatus;
	public String validationMessage;
	/**
	 * @return the validationStatus
	 */
	public boolean isValidationStatus() {
		return validationStatus;
	}
	/**
	 * @param validationStatus the validationStatus to set
	 */
	public void setValidationStatus(boolean validationStatus) {
		this.validationStatus = validationStatus;
	}
	/**
	 * @return the validationMessage
	 */
	public String getValidationMessage() {
		return validationMessage;
	}
	/**
	 * @param validationMessage the validationMessage to set
	 */
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
	@Override
	public String toString() {
		return "validationMessage{" +
				"validationStatus= " + validationStatus +
				"validationMessage= " + validationMessage +
				'}';
		
	}

}
