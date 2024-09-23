package malicedevelopment.tardis.access;

public interface RegenerationDataAccess {

	public void setRegenerationTicksElapsed(int regenerationTicksElapsed);

	public void setRegensLeft(int regensLeft);

	public int getRegenerationTicksElapsed();

	public int getRegensLeft();

	public boolean isRegenerating();

	public void setSkin(String skin);
	public String getSkin();

	public boolean isSlim();
	public void setSlim(boolean slim);
}
