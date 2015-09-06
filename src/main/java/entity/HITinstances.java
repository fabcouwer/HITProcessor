package entity;

import java.util.ArrayList;
import java.util.HashMap;

// Tracks a number of HITinstance entities for a single HITgroup.
public class HITinstances {

	@SuppressWarnings("unused")
	private String groupID;
	private ArrayList<Long> timestamps;
	private HashMap<Long, Integer> hitsDiff;
	private HashMap<Long, Integer> hitsAvailable;

	public HITinstances(String ID) {
		this.groupID = ID;
		timestamps = new ArrayList<Long>();
		hitsDiff = new HashMap<Long, Integer>();
		hitsAvailable = new HashMap<Long, Integer>();
	}

	public void addTimestamp(long timestamp, int hitsDiff, int hitsAvailable) {
		Long currentTS = new Long(timestamp);
		this.timestamps.add(currentTS);
		this.hitsDiff.put(currentTS, hitsDiff);
		this.hitsAvailable.put(currentTS, hitsAvailable);
	}

	public double calculateThroughput() {
		if (this.timestamps.isEmpty()) {
			return 0;
		} else {
			// Get start and end timestamps
			Long start = this.timestamps.get(0);
			Long end = this.timestamps.get(0);

			for (Long current : this.timestamps) {
				if (current < start)
					start = current;
				if (current > end)
					end = current;
			}

			int initialHITs = hitsAvailable.get(start);
			int finalHITs = hitsAvailable.get(end);

			// If the HIT group was not completed, we use the end of tracking
			// (1439290800000L) as an endpoint
			if (hitsAvailable.get(end) != 0) {
				end = new Long(1439290800000L);
			}

			// Uptime in hours = (end - start) / 3600000
			double uptime = (end.doubleValue() - start.doubleValue()) / 3600000;
			// Get amount of HITs completed
			double hitsCompleted = initialHITs - finalHITs;
			if (hitsCompleted < 0) {
				hitsCompleted = 0;
			}

			// Throughput = hitsCompleted / uptime in hours
			double throughput = hitsCompleted / uptime;

			return throughput;
		}
	}
}
