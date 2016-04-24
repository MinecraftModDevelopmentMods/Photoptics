package abr.mod.photoptics;

public enum EnumPhotopticsKeys {
	
	ZoomIn(true), ZoomOut(true);
	
	private boolean continuous;
	
	EnumPhotopticsKeys(boolean conti) {
		this.continuous = conti;
	}
	
	public boolean isContinuous() {
		return this.continuous;
	}

}
