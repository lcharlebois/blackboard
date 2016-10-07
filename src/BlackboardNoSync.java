import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author Charles Montigny
 *
 * BlackBoard implementation.
 * @param <T> Type of objects managed by the blackboard
 *
 * This should be used to organize work flow.
 * Producers posts <T> here, customers reads <T> here.
 * Customers may wait on <T> to be produced.
 *
 * This object is Thread safe, you may use it without external synchronization.
 *
 * Synchronization of <T> is not managed. Each <T> instance is shared among all registrants.
 * <T> should be non-mutable objects, otherwise, use it with care.
 *
 * TODO all the synchronization
 *
 */
public class BlackboardNoSync<T>
{
	/**
	 * List of registrant, for each of them, keeps a list of <T>.
	 * The HashMap synchronization is guarded by "this" instance.
	 * The ArrayList<T> synchronization is guarded by itself.
	 */
	private HashMap<Object, ArrayList<T>> mRegistrantList;

	/**
	 * Object used to synchronize the posts and reads.
	 * Read will wait on this object, Post will notify this object.
	 * This Object's synchronization is guarded by itself.
	 */
	private Object wSynchronizer;

	public BlackboardNoSync()
	{
		mRegistrantList = new HashMap<Object, ArrayList<T>>();
		wSynchronizer = new Object();
	}

	public void register(Object iRegistrant)
	{
		if(!mRegistrantList.containsKey(iRegistrant))
		{
			mRegistrantList.put(iRegistrant, new ArrayList<T>());
		}
	}

	public void post(T iT)
	{
		synchronized(wSynchronizer){
			ArrayList<T> wValueList;
			for(Object wRegistrant : mRegistrantList.keySet())
			{
				wValueList = mRegistrantList.get(wRegistrant);
				wValueList.add(iT);
			}
		}
	}

	public T read(Object iRegistrant) throws InterruptedException
	{
		return read(iRegistrant, false);
	}

	public T read(Object iRegistrant, boolean iBlocking) throws InterruptedException
	{
		synchronized(wSynchronizer){
			ArrayList<T> wValueList = getRegistrantValueList(iRegistrant);
			int wSize;
			wSize = wValueList.size();
			while(wSize < 1)
			{
				if(iBlocking) {;}
				else
				{
					return null;
				}
				wSize = wValueList.size();
			}
			T wFirstValue;
			wFirstValue= wValueList.remove(0);
			return wFirstValue;
		}
	}

	private ArrayList<T> getRegistrantValueList(Object iRegistrant)
	{
		register(iRegistrant);
		return mRegistrantList.get(iRegistrant);
	}
}
