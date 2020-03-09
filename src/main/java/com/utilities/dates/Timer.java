package com.utilities.dates;

public class Timer {
   long lgStart = 0L;
   long lgEnd = 0L;

   public void start() {
      this.lgStart = System.currentTimeMillis();
   }

   public void end() {
      this.lgEnd = System.currentTimeMillis();
   }

   public void reset() {
      this.lgStart = 0L;
      this.lgEnd = 0L;
   }
}
