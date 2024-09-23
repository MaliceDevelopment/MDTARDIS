package malicedevelopment.tardis.access;

public interface RegenerationDataAccess {

	public void setRegenerationTicksElapsed(int regenerationTicksElapsed);

	public void setRegensLeft(int regensLeft);

	public int getRegenerationTicksElapsed();

	public int getRegensLeft();

	public boolean isRegenerating();
}
