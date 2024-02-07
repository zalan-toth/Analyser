package net.pyel.models;

/**
 * Terminal Element model - used for each line in terminal
 *
 * @author Zalán Tóth
 */
public class TerminalElement {
	private String outPutView;
	private Object inspectionElemenet;

	public TerminalElement(String outPutView, Object inspectionElemenet) {
		this.outPutView = outPutView;
		this.inspectionElemenet = inspectionElemenet;
	}

	public String getOutPutView() {
		return outPutView;
	}

	public void setOutPutView(String outPutView) {
		this.outPutView = outPutView;
	}

	public Object getInspectionElemenet() {
		return inspectionElemenet;
	}

	public void setInspectionElemenet(Object inspectionElemenet) {
		this.inspectionElemenet = inspectionElemenet;
	}

	@Override
	public String toString() {
		return outPutView;
	}
}
