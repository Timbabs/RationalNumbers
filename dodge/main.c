// Name: Timothy Baba
#include "mylib.h"
#include "stars.h"
#include "hulk.h"
#include "image1.h"
#include "image2.h"
#include "image3.h"
#include "stars2.h"
#include "image4.h"
#include "duck.h"
#include "image6.h"

volatile u32* KEYS = (volatile u32*)0x04000130;


extern int level;
extern int justBegan;

enum GBAState {
	START,
	START_NODRAW,
	STATE1,
	STATE1_NODRAW,
	STATE2,
	DUMY1,
	DUMY2,
	LEVEL_CHANGE
};
extern enum GBAState state;



void gameInit();
void play();
void showFirstPage();


int main() {
	
	REG_DISPCNT = MODE_3 | BG2_EN;
	
	while(1) {
		waitForVBlank();
		switch(state) {
			case START:
				showFirstPage();
				state = START_NODRAW;
				
			case START_NODRAW:

				while(KEY_DOWN_NOW(KEY_START)) {
					state = STATE1;
				}
				
				break;

			case STATE1:
				gameInit();
				justBegan = 0;
				drawString(50, 40, "You are about to Start", BLACK);
				drawString(50, 100, "Press <ENTER> TO Continue" , BLACK);
				state = STATE1_NODRAW;
				break;	

			case STATE1_NODRAW:
				while(KEY_DOWN_NOW(KEY_START)) {
					gameInit();
					drawRect(20, 150, 200, 10 , GREEN);
					drawRect(29, 134, 180, 10 , GREEN);
					state = STATE2;
				}
				while(KEY_DOWN_NOW(KEY_SELECT)) {
					state = START;
				}
				break;

			case STATE2:
				play();
				break;

			case DUMY1:
				drawRect(0, 0, 240, 160, BLACK);
					drawImage3(29, 19, 240 - 2 * (29 + 1), 160 - 2 * (19 + 1), image1);
					drawRect(20, 150, 200, 10 , GREEN);
					drawString(60, 90, "<ENTER> TO RESTART" , WHITE);
					state = LEVEL_CHANGE;
					break;

			case DUMY2:
					drawImage3(29, 19, 240 - 2 * (29 + 1), 160 - 2 * (19 + 1), stars);
					if(level == 2) {
						drawString(48, 80, "Welcome to round 2!!!" , WHITE);
						drawString(60, 90, "<ENTER> TO BEGIN" , WHITE);
						drawRect(20, 150, 200, 10 , GREEN);
						level = 2;
					} else {
						drawImage3(29, 19, 240 - 2 * (29 + 1), 160 - 2 * (19 + 1), image6);
						drawString(60, 95, "<ENTER> TO RESTART" , WHITE);
						drawRect(20, 150, 200, 10 , GREEN);
						
					}
					state = LEVEL_CHANGE;
					
					break;


			case LEVEL_CHANGE:

				if(KEY_DOWN_NOW(KEY_START)) {
					state = STATE1_NODRAW;
					
				}
				if(KEY_DOWN_NOW(KEY_SELECT)) {
					justBegan = 1;
					state = START;
				}
	
				break;
		}
	}


	return 0;
}
