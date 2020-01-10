package player;

public class MediaInfo {

	private String mediaName;
	private int index;

	MediaInfo(int index, String mediaName){
		this.setIndex(index);
		this.setMediaName(mediaName);
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
