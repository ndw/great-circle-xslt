package com.nwalsh.saxon.gcd;

public class GCD {
  public GCD() {
  }

  public static double distance (double lat1, double long1,
				 double lat2, double long2,
				 double radius) {
    // See http://en.wikipedia.org/wiki/Great_circle_distance
    double lat1r = Math.toRadians(lat1);
    double long1r = Math.toRadians(long1);
    double lat2r = Math.toRadians(lat2);
    double long2r = Math.toRadians(long2);

    double s1 = Math.sin((lat1r - lat2r) / 2.0);
    double s2 = Math.sin((long1r - long2r) / 2.0);
    double i1 = (s1*s1) + (Math.cos(lat1r)*Math.cos(lat2r)*(s2*s2));

    return 2 * radius * Math.asin(Math.sqrt(i1));
  }

  public static double direction (double lat1, double long1,
				  double lat2, double long2) {
    double lat1r = Math.toRadians(lat1);
    double long1r = Math.toRadians(long1);
    double lat2r = Math.toRadians(lat2);
    double long2r = Math.toRadians(long2);
    double dist = distance(lat1, long1, lat2, long2, 1.0);

    if (dist < 0.0001) {
      // If lat1,long1 is too close to lat2,long2, just say "north"
      return 0.0;
    }

    double s1 = Math.sin(lat2r) - Math.cos(dist+lat1r-(Math.PI/2.0));
    double s2 = s1 / Math.cos(lat1r) / Math.sin(dist);
    double a  = Math.acos(s2 + 1);

    double dlong = long1r - long2r;

    if ((Math.abs(dlong) < Math.PI && dlong < 0)
	|| Math.abs(dlong) > Math.PI && dlong > 0) {
      a = (2.0 * Math.PI) - a;
    }

    return Math.toDegrees(a);
  }

  public static String bearing (double lat1, double long1,
				double lat2, double long2) {
    double a = direction(lat1, long1, lat2, long2);

    // Hack!
    if (a > 337.5) return "N";
    if (a > 292.5) return "NW";
    if (a > 247.5) return "W";
    if (a > 202.5) return "SW";
    if (a > 157.5) return "S";
    if (a > 112.5) return "SE";
    if (a >  67.5) return "E";
    if (a >  22.5) return "NE";
    return "N";
  }
}
