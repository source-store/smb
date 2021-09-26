package ru.smb.smb.service;

/*
 * @author Alexandr.Yakubov
 **/

public class GeoService {

  private static double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
    float pk = (float) (180/Math.PI);

    float a1 = lat_a / pk;
    float a2 = lng_a / pk;
    float b1 = lat_b / pk;
    float b2 = lng_b / pk;

    double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
    double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
    double t3 = Math.sin(a1)*Math.sin(b1);
    double tt = Math.acos(t1 + t2 + t3);

    return 6366000*tt;
  }

  public static void main(String[] args) {
    System.out.println(gps2m(37.6f,55.6f, 37.601f,55.6f));
  }

}