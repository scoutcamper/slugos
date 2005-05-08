 #include <stdio.h>
 #include <stdlib.h>
 #include <math.h>
 #include <errno.h>
 #include <string.h>
 #include <endian.h>
 #include <unistd.h>
 #include <fcntl.h>
 #include <sys/ioctl.h>
 #include "leds.h"

 static int leds;
 static int e1;
 static int toggle[PHYS_LEDS];


 void init_leds(void)
 {
   if ((leds = open("/dev/leds", O_RDWR)) < 0) {
     e1 = errno;
     if (e1 != ENOENT) {

       fprintf(stderr,"Error: Could not open LEDS device file '/dev/leds' : %s\n",
               strerror(e1));
       if(e1 == EACCES)
         fprintf(stderr,"Run as root\n");
     }
   }

   // Init toggle array
   for (e1 = 0; e1 < PHYS_LEDS; e1++)
      toggle[e1] = 0;

   // reset all leds to off
   if (ioctl(leds, N2_LM_ALL_OFF, 0) < 0) {
     fprintf(stderr,
             "Error: Could not access the leds: %s\n",
             strerror(errno));
   }

   printf("\n\rleds initialized.");
 }

 void led_on( int num )
 {
   if (ioctl(leds, N2_LM_ON, num) < 0) {
     fprintf(stderr,
             "Error: Could not access the leds: %s\n",
             strerror(errno));
   }
 }

 void led_off( int num )
 {
   if (ioctl(leds, N2_LM_OFF, num) < 0) {
     fprintf(stderr,
             "Error: Could not access the leds: %s\n",
             strerror(errno));
   }
 }

 void led_toggle(int num)
 {
   if (toggle[num])
   {
     e1 = ioctl(leds, N2_LM_OFF, num);
     toggle[num] = 0;
   }
   else
   {
     e1 = ioctl(leds, N2_LM_ON, num);
     toggle[num] = 1;
   }

   if (e1 < 0) {
     fprintf(stderr,
             "Error: Could not access the leds: %s\n",
             strerror(errno));
   }
 }
 
 main () 
 {
 	init_leds();
/*	led_on (LED_DISK1); */
/* 	led_on (LED_DISK2); */
	led_on (LED_RS_GRN); 
 }
 
 
