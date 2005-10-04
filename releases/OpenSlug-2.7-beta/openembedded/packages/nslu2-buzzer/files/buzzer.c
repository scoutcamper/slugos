/* Buzzer.c
*
* Using details from 'tman' and 'kas11', based on howto
* ProgrammingTheOpenSlugLEDs
*
* Code licensed with GPL
*
* Close encounters of the third kind, well sort of.
* buzzer 12 -250 7 -250 11 -250 2 -250 9
*/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>
#include <string.h>
#include <endian.h>
#include <unistd.h>
#include <fcntl.h>
#include <asm/system.h>
#include <sys/ioctl.h>
#include <sys/file.h>
#include <time.h>

//one beep at current defaults
#define N2_BZ_BEEP _IO('M',1)
//set tone: range is high=250 to low=2000
#define N2_BZ_TONESET _IOW('M',4,long)

#define NOTES 22
int progression[NOTES] = {2000, 1850, 1760, 1550, 1500, 1400, 1300,
1200, 1100, 1000, 900, 810, 720, 630, 580, 520, 480, 430, 390, 350,
300, 260};


static int buzzer;
static int e1;

int init_buzzer(void) {
	if ((buzzer = open("/dev/buzzer", O_RDWR)) < 0) {
		e1 = errno;
		if (e1 != ENOENT) {
			if (e1 == EACCES) return(0);
		}
	}
	flock(buzzer, LOCK_EX);
	return(1);
}


void buzz(int freq) {
	// Set frequency
	if (ioctl(buzzer, N2_BZ_TONESET, freq) < 0 ) {
		fprintf(stderr,
		"Error: Could not access the buzzer: %s\n",
		strerror(errno));
	}
	// do the beep
 	if (ioctl(buzzer, N2_BZ_BEEP, 0) < 0) {
		fprintf(stderr,
		"Error: Could not access the buzzer: %s\n",
		strerror(errno));
	}
}


int stop_buzzer(void) {
	flock(buzzer, LOCK_UN);
	close(buzzer);
}


void wait(int milli_secs) {
	struct timespec t;
	t.tv_sec = milli_secs/1000;
	t.tv_nsec = 1000000 *(milli_secs%1000);
	nanosleep(&t,0);
}


int main(int argc, char **argv) {
	int freq, length;
	char *binary;
	char c;
	binary = *argv;
	if (argc < 2) {printf("Usage: %s <tone 0-21| freq 2000-250 | pause -millisec> [...]\n", binary); return 0;}
	if (!init_buzzer()) {printf("Run as Root\n"); return 0;}
	argv++;
	c = *(argv)[0];
	if (((c < 48) || (c > 58)) && (c != '-')) {printf("Usage: %s <tone 0-21| freq 2000-250| p -millisec> [...]\n", binary); return 0;}
	int x;
	for (x = 1; x < argc; x++) {
	c = *(argv)[0];
	if (((c < 48) || (c > 58)) && (c != '-')) {printf("Usage: %s <tone 0-21| freq 2000-250| p -millisec> [...]\n", binary); return 0;}
	if ((*argv[0] == '-') && (*(*argv+1) != 0)) {
		sscanf(*argv+1,"%d",&freq);
		wait(freq);
	}
	else {
		if (!( (*argv[0] < 48) || (*argv[0] > 58) )) sscanf(*argv,"%d",&freq);
		if (freq < 250) if (freq < NOTES) freq = progression[freq];
		else freq = 1550;
		buzz(freq);
	}
	argv++;
}

stop_buzzer();
return 1;
}
