package mvc;

public interface IViewOutput {
	void setDepth(String depthStr);
	void setRunCount(String runCounuStr);
	void setImageRunCount(String imageRunCountStr);
	void setImageSuccessCount(String imageSuccessCountStr);
	void setImageSmallCount(String imageSmallCountStr);
	void setVisitedCount(String visitedCountStr);
	void setExceptionCount(String exceptionCountStr);

}
