package entity;

import java.util.ArrayList;
import java.util.HashMap;

// Tracks a number of HITinstance entities for a single HITgroup.
public class HITinstances {

	@SuppressWarnings("unused")
	private String groupID;
	private ArrayList<Long> timestamps;
	private Long firstMeasurement;
	private Long lastMeasurement;
	private HashMap<Long, Integer> hitsDiff;
	private HashMap<Long, Integer> hitsAvailable;

	public HITinstances(String ID) {
		this.groupID = ID;
		timestamps = new ArrayList<Long>();
		hitsDiff = new HashMap<Long, Integer>();
		hitsAvailable = new HashMap<Long, Integer>();
		firstMeasurement = Long.MAX_VALUE;
		lastMeasurement = Long.MIN_VALUE;
	}

	public void addTimestamp(long timestamp, int hitsDiff, int hitsAvailable) {
		Long currentTS = new Long(timestamp);
		this.timestamps.add(currentTS);
		this.hitsDiff.put(currentTS, hitsDiff);
		this.hitsAvailable.put(currentTS, hitsAvailable);

		if (timestamp < firstMeasurement)
			firstMeasurement = timestamp;
		if (timestamp > lastMeasurement)
			lastMeasurement = timestamp;
	}

	@Deprecated
	public double calculateOldThroughput() {
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

	public int getInitialHits() {
		if (!this.timestamps.isEmpty()) {
			// Get start timestamp and its amount of hits
			Long start = this.timestamps.get(0);

			for (Long current : this.timestamps) {
				if (current < start)
					start = current;
			}

			return this.hitsAvailable.get(start).intValue();
		} else
			return 0;

	}

	// Gets the throughput of this HITgroup in the hour preceding timestamp
	public double getThroughput(Long timestamp) {
		// Get all timestamps of the hour before given timestamp
		ArrayList<Long> validTimestamps = new ArrayList<Long>();
		long difference = -1;
		for (Long current : this.timestamps) {
			difference = timestamp.longValue() - current.longValue();
			// For t within the previous hour t must be 0 <= t <= 3600000
			if (difference >= 0L && difference <= 3600000L)
				validTimestamps.add(current);
		}

		if (validTimestamps.isEmpty()) {
			// No measurements in this hour - check if it falls into our
			// measurement range
			if (firstMeasurement < timestamp && timestamp < lastMeasurement)
				return 0;
			else
				return Double.MIN_VALUE;
		} else {
			// Result is the combined hitsDiff for the measurements.
			// If hitsDiff = -N, N hits were completed.
			// So we multiply by -1 to get throughput.
			double result = 0;
			for (Long current : validTimestamps) {
				result += (-1 * hitsDiff.get(current));
			}
			if (result < 0)
				result = 0;
			return result;
		}

	}
}
