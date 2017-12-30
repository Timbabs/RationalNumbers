// Name: Timothy Baba
#include "mylib.h"
#include "hulk.h"
#include "stars.h"
#include "image1.h"
#include "image2.h"
#include "image3.h"
#include "stars2.h"
#include "image4.h"
#include "duck.h"
#include "image6.h"

// structs of the player enemies and food
typedef struct {
	int x;
	int y;
	int v;
	int size;
} playerStruct;

typedef struct {
	int x;
	int y;
	int v;
	int size;
	u16 color; 
} enemy;

typedef struct {
	int x;
	int y;
	int size;
	int eaten;
} food;


playerStruct player = {0, 20, 2, HULK_HEIGHT};
enemy enemy1 = {39, 19, 5, 5, BLACK};
enemy enemy2 = {69, 129, -10, 5, BLACK};
enemy enemy3 = {99, 19, 5, 5, BLACK};
enemy enemy4 = {129, 129, -10, 5, BLACK};
enemy enemy5 = {159, 19, 5, 5, BLACK};
enemy enemy6 = {189, 129, -10, 5, BLACK};
food food1 = {54,80, 0, 10};
food food2 = {120,40, 0, 10};
food food3 = {197,25, 0, 10};
food food4 = {69, 50, 0, 10};
food food5 = {195, 60, 0, 10};
food food6 = {35, 130, 0, 10};
food food7 = {35, 25, 0, 10};
food food8 = {99, 90, 0, 10};


int danger_upper_bound = 19;
int danger_lower_bound = 139;
int danger_left_bound = 29;
int danger_right_bound = 209;




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

enum GBAState state = START;

int game_over = 0;
int count = 0;
int level = 1;
int justBegan = 1;

int prev_x = 120;
int prev_y = 125;

// function prototypes
void showFirstPage();
void gameInit();
void draw(u16 color);
void clearDraw(const unsigned short *image);
void play();
static void movePlayer();
static void moveEnemies();
static void foodEaten();
static int collision();

static void defineBoundaries();


void showFirstPage(){
	drawImage3(3, 4, IMAGE3_WIDTH, IMAGE3_HEIGHT, image3);
	drawString(66, 128, "<ENTER> TO START" , WHITE);
	drawString(20, 140, "<BACKSPACE> To return to this page", WHITE);
	drawString(50, 150, "at any time of the game" , WHITE);
}

void gameInit() {

	drawImage3(3, 4, IMAGE2_WIDTH, IMAGE2_HEIGHT, image2);
	
	if(justBegan == 1) {
		drawImage3(danger_left_bound, danger_upper_bound, 240 - 2 * (danger_left_bound + 1), 160 - 2 * (danger_upper_bound + 1), image4);
	} else {
		drawRect(danger_left_bound, danger_upper_bound, 240 - 2 * (danger_left_bound + 1), 160 - 2 * (danger_upper_bound + 1), GREEN);
	}
	
	player.x = 120;
	player.y = 123;
	player.v = 5;

	enemy1.x = 39;
	enemy1.y = 19;
	enemy1.v = -3;

	enemy2.x = 69;
	enemy2.y = 19;
	enemy2.v = -5;

	enemy3.x = 99;
	enemy3.y = 19;
	enemy3.v = -3;
	
	enemy4.x = 129;
	enemy4.y = 19;
	enemy4.v = -5;

	enemy5.x = 159; 
	enemy5.y = 19;
	enemy5.v = -3;

	enemy6.x = 189;
	enemy6.y = 19;
	enemy6.v = -5;


	food1.x = 54;
	food1.y = 80;
	food1.eaten = 0;
	food2.x = 120;
	food2.y = 40;
	food2.eaten = 0;
	food3.x = 197;
	food3.y = 25;
	food3.eaten = 0;


	food4.x = 69 ;
	food4.y = 50;
	food4.eaten = 0;
	food5.x = 195 ;
	food5.y = 60;
	food5.eaten = 0;
	food6.x = 35;
	food6.y = 130;
	food6.eaten = 0;
	food7.x = 35;
	food7.y = 25;
	food7.eaten = 0;
	food8.x = 99;
	food8.y = 90;
	food8.eaten = 0;
	

	drawImage3(player.x, player.y, HULK_WIDTH, HULK_HEIGHT, hulk);
	draw(enemy1.color); draw(enemy2.color); draw(enemy3.color); draw(enemy4.color); draw(enemy5.color);
	draw(enemy6.color);

	count = 0;
	game_over = 0;
	if (KEY_DOWN_NOW(KEY_SELECT)) {
		//start_game = 0;
		justBegan = 1;
		state = START;
		showFirstPage();
	}
	waitForVBlank();
}

void draw(u16 color) {
	drawRect(enemy1.x, enemy1.y, enemy1.size, enemy1.size*2, color);
	drawRect(enemy1.x - 4, enemy1.y + 2, enemy1.size - 1, enemy1.size -1, color);
	drawRect(enemy1.x + 5, enemy1.y + 2, enemy1.size -1, enemy1.size -1, color);


	drawRect(enemy2.x, enemy2.y, enemy2.size, enemy2.size*2, color);
	drawRect(enemy2.x - 4, enemy2.y + 2, enemy2.size - 1, enemy2.size -1, color);
	drawRect(enemy2.x + 5, enemy2.y + 2, enemy2.size -1, enemy2.size -1, color);

	drawRect(enemy3.x, enemy3.y, enemy3.size, enemy3.size*2, color);
	drawRect(enemy3.x - 4, enemy3.y + 2, enemy3.size - 1, enemy3.size -1, color);
	drawRect(enemy3.x + 5, enemy3.y + 2, enemy3.size -1, enemy3.size -1, color);


	drawRect(enemy4.x, enemy4.y, enemy4.size, enemy4.size*2, color);
	drawRect(enemy4.x - 4, enemy4.y + 2, enemy4.size - 1, enemy4.size -1, color);
	drawRect(enemy4.x + 5, enemy4.y + 2, enemy4.size -1, enemy4.size -1, color);

	drawRect(enemy5.x, enemy5.y, enemy5.size, enemy5.size*2, color);
	drawRect(enemy5.x - 4, enemy5.y + 2, enemy5.size - 1, enemy5.size -1, color);
	drawRect(enemy5.x + 5, enemy5.y + 2, enemy5.size -1, enemy5.size -1, color);

	drawRect(enemy6.x, enemy6.y, enemy6.size, enemy6.size*2, color);
	drawRect(enemy6.x - 4, enemy6.y + 2, enemy6.size - 1, enemy6.size -1, color);
	drawRect(enemy6.x + 5, enemy6.y + 2, enemy6.size -1, enemy6.size -1, color);
}



void play() {
	
	drawRect(prev_x, prev_y, HULK_WIDTH, HULK_HEIGHT, GREEN);
	drawImage3(player.x, player.y, HULK_WIDTH, HULK_HEIGHT, hulk);
	
	draw(GREEN);


	prev_x = player.x;
	prev_y = player.y;
	// move player
	movePlayer();
	foodEaten();
	if(food1.eaten == 1) {
		drawRect(food1.x, food1.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
		count++;
	} else {
		drawImage3(food1.x, food1.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
	}

	if(food2.eaten == 1) {
		drawRect(food2.x, food2.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
		count++;
	} else {
		drawImage3(food2.x, food2.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
	}
	if(food3.eaten == 1) {
		drawRect(food3.x, food3.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
		count++;
	} else {
		drawImage3(food3.x, food3.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
	}

	if(level == 2) {
		if(food4.eaten == 1) {
		drawRect(food4.x, food4.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
		count++;
		} else {
			drawImage3(food4.x, food4.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
		}

		if(food5.eaten == 1) {
			drawRect(food5.x, food5.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
			count++;
		} else {
			drawImage3(food5.x, food5.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
		}
		if(food6.eaten == 1) {
			drawRect(food6.x, food6.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
			count++;
		} else {
			drawImage3(food6.x, food6.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
		}
		if(food7.eaten == 1) {
			drawRect(food7.x, food7.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
			count++;
		} else {
			drawImage3(food7.x, food7.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
		}
		if(food8.eaten == 1) {
			drawRect(food8.x, food8.y, DUCK_WIDTH, DUCK_HEIGHT , GREEN);
			count++;
		} else {
			drawImage3(food8.x, food8.y, DUCK_WIDTH, DUCK_HEIGHT , duck);
		}

	}

	// draw objects within boundaries
	defineBoundaries();

	// move enemies	
	moveEnemies();

	if(KEY_DOWN_NOW(KEY_A)) {
			int pause = 0;
				drawRect(20, 150, 200, 10 , GREEN);
				drawString(58, 152, "<ENTER> TO CONTINUE" , BLACK);
			while(pause == 0) {
				
				
				if(KEY_DOWN_NOW(KEY_START)) {
					pause = 1;
				}
			}
			drawRect(20, 150, 200, 10 , GREEN);
			
	}

	//collesion detection
	if (collision() == 1) {
		game_over = 1;
		if (KEY_DOWN_NOW(KEY_SELECT)) {
			//start_game = 0;
			justBegan = 1;
			state = START;
			showFirstPage();
		}
	}

	// check if the game is over or not
	if (game_over == 0) {
		//drawRect(20, 150, 200, 10 , GREEN);
		drawString(20, 151, "IN PROGRESS..." , BLACK);
		drawString(110, 151, "Press <Z> to PAUSE" , BLACK);

	} else {
		
		state = DUMY1;
		player.v = 0;
			enemy1.v = 0;
			enemy2.v = 0;
			enemy3.v = 0;
			enemy4.v = 0;
			enemy5.v = 0;
			enemy6.v = 0;

	}

	// check if the player wins
	//if (player.x >= danger_right_bound) {
	if(level == 1) {
		if (food1.eaten + food2.eaten + food3.eaten == 3) {
			level = 2;
			state = DUMY2;
			player.v = 0;
			enemy1.v = 0;
			enemy2.v = 0;
			enemy3.v = 0;
			enemy4.v = 0;
			enemy5.v = 0;
			enemy6.v = 0;
		
		}
	} else {
		if (food1.eaten + food2.eaten + food3.eaten + food4.eaten + food5.eaten + food6.eaten + food7.eaten + food8.eaten == 8) {
			level = 1;
			state = DUMY2;

	
			player.v = 0;
			enemy1.v = 0;
			enemy2.v = 0;
			enemy3.v = 0;
			enemy4.v = 0;
			enemy5.v = 0;
			enemy6.v = 0;
		}
	}
	
	if (KEY_DOWN_NOW(KEY_SELECT)) {
		//start_game = 0;
		justBegan = 1;
		state = START; 
		showFirstPage();
	}
	

	waitForVBlank();
	delay(1);
}


static int collision() {

	if ((player.x <= enemy1.x && player.x + player.size >= enemy1.x && 
		 	player.y <= enemy1.y && player.y + player.size >= enemy1.y) ||
		(player.x <= enemy1.x && player.x + player.size >= enemy1.x && 
			player.y <= enemy1.y + enemy1.size && player.y + player.size >= enemy1.y + enemy1.size) ||
		(player.x <= enemy1.x + enemy1.size && player.x + player.size >= enemy1.x + enemy1.size && 
			player.y <= enemy1.y && player.y + player.size >= enemy1.y) ||
		(player.x <= enemy1.x + enemy1.size && player.x + player.size >= enemy1.x + enemy1.size && 
			player.y <= enemy1.y + enemy1.size && player.y + player.size >= enemy1.y + enemy1.size) ||

		(player.x <= enemy2.x && player.x + player.size >= enemy2.x && 
		 	player.y <= enemy2.y && player.y + player.size >= enemy2.y) ||
		(player.x <= enemy2.x && player.x + player.size >= enemy2.x && 
			player.y <= enemy2.y + enemy2.size && player.y + player.size >= enemy2.y + enemy2.size) ||
		(player.x <= enemy2.x + enemy2.size && player.x + player.size >= enemy2.x + enemy2.size && 
			player.y <= enemy2.y && player.y + player.size >= enemy2.y) ||
		(player.x <= enemy2.x + enemy2.size && player.x + player.size >= enemy2.x + enemy2.size && 
			player.y <= enemy2.y + enemy2.size && player.y + player.size >= enemy2.y + enemy2.size) ||

		(player.x <= enemy3.x && player.x + player.size >= enemy3.x && 
		 	player.y <= enemy3.y && player.y + player.size >= enemy3.y) ||
		(player.x <= enemy3.x && player.x + player.size >= enemy3.x && 
			player.y <= enemy3.y + enemy3.size && player.y + player.size >= enemy3.y + enemy3.size) ||
		(player.x <= enemy3.x + enemy3.size && player.x + player.size >= enemy3.x + enemy3.size && 
			player.y <= enemy3.y && player.y + player.size >= enemy3.y) ||
		(player.x <= enemy3.x + enemy3.size && player.x + player.size >= enemy3.x + enemy3.size && 
			player.y <= enemy3.y + enemy3.size && player.y + player.size >= enemy3.y + enemy3.size) ||

		(player.x <= enemy4.x && player.x + player.size >= enemy4.x && 
		 	player.y <= enemy4.y && player.y + player.size >= enemy4.y) ||
		(player.x <= enemy4.x && player.x + player.size >= enemy4.x && 
			player.y <= enemy4.y + enemy4.size && player.y + player.size >= enemy4.y + enemy4.size) ||
		(player.x <= enemy4.x + enemy4.size && player.x + player.size >= enemy4.x + enemy4.size && 
			player.y <= enemy4.y && player.y + player.size >= enemy4.y) ||
		(player.x <= enemy4.x + enemy4.size && player.x + player.size >= enemy4.x + enemy4.size && 
			player.y <= enemy4.y + enemy4.size && player.y + player.size >= enemy4.y + enemy4.size) ||

		(player.x <= enemy5.x && player.x + player.size >= enemy5.x && 
		 	player.y <= enemy5.y && player.y + player.size >= enemy5.y) ||
		(player.x <= enemy5.x && player.x + player.size >= enemy5.x && 
			player.y <= enemy5.y + enemy5.size && player.y + player.size >= enemy5.y + enemy5.size) ||
		(player.x <= enemy5.x + enemy5.size && player.x + player.size >= enemy5.x + enemy5.size && 
			player.y <= enemy5.y && player.y + player.size >= enemy5.y) ||
		(player.x <= enemy5.x + enemy5.size && player.x + player.size >= enemy5.x + enemy5.size && 
			player.y <= enemy5.y + enemy5.size && player.y + player.size >= enemy5.y + enemy5.size) ||

		(player.x <= enemy6.x && player.x + player.size >= enemy6.x && 
		 	player.y <= enemy6.y && player.y + player.size >= enemy6.y) ||
		(player.x <= enemy6.x && player.x + player.size >= enemy6.x && 
			player.y <= enemy6.y + enemy6.size && player.y + player.size >= enemy6.y + enemy6.size) ||
		(player.x <= enemy6.x + enemy6.size && player.x + player.size >= enemy6.x + enemy6.size && 
			player.y <= enemy6.y && player.y + player.size >= enemy6.y) ||
		(player.x <= enemy6.x + enemy6.size && player.x + player.size >= enemy6.x + enemy6.size && 
			player.y <= enemy6.y + enemy6.size && player.y + player.size >= enemy6.y + enemy6.size)) {
			
			return 1; 
		} else {
			return 0;
		}
}
static void foodEaten() {
	if ((player.x <= food1.x && player.x + player.size >= food1.x && 
		 	player.y <= food1.y && player.y + player.size >= food1.y) ||
		(player.x <= food1.x && player.x + player.size >= food1.x && 
			player.y <= food1.y + food1.size && player.y + player.size >= food1.y + food1.size) ||
		(player.x <= food1.x + food1.size && player.x + player.size >= food1.x + food1.size && 
			player.y <= food1.y && player.y + player.size >= food1.y) ||
		(player.x <= food1.x + food1.size && player.x + player.size >= food1.x + food1.size && 
			player.y <= food1.y + food1.size && player.y + player.size >= food1.y + food1.size)) {
			food1.eaten = 1;
		} 	

	if ((player.x <= food2.x && player.x + player.size >= food2.x && 
		 	player.y <= food2.y && player.y + player.size >= food2.y) ||
		(player.x <= food2.x && player.x + player.size >= food2.x && 
			player.y <= food2.y + food2.size && player.y + player.size >= food2.y + food2.size) ||
		(player.x <= food2.x + food2.size && player.x + player.size >= food2.x + food2.size && 
			player.y <= food2.y && player.y + player.size >= food2.y) ||
		(player.x <= food2.x + food2.size && player.x + player.size >= food2.x + food2.size && 
			player.y <= food2.y + food2.size && player.y + player.size >= food2.y + food2.size)) {
			food2.eaten = 1;
		} 	

	if ((player.x <= food3.x && player.x + player.size >= food3.x && 
		 	player.y <= food3.y && player.y + player.size >= food3.y) ||
		(player.x <= food3.x && player.x + player.size >= food3.x && 
			player.y <= food3.y + food3.size && player.y + player.size >= food3.y + food3.size) ||
		(player.x <= food3.x + food3.size && player.x + player.size >= food3.x + food3.size && 
			player.y <= food3.y && player.y + player.size >= food3.y) ||
		(player.x <= food3.x + food3.size && player.x + player.size >= food3.x + food3.size && 
			player.y <= food3.y + food3.size && player.y + player.size >= food3.y + food3.size)) {
			food3.eaten = 1;
		} 

	if(level == 2) {
		if ((player.x <= food4.x && player.x + player.size >= food4.x && 
		 	player.y <= food4.y && player.y + player.size >= food4.y) ||
		(player.x <= food4.x && player.x + player.size >= food4.x && 
			player.y <= food4.y + food4.size && player.y + player.size >= food4.y + food4.size) ||
		(player.x <= food4.x + food4.size && player.x + player.size >= food4.x + food4.size && 
			player.y <= food4.y && player.y + player.size >= food4.y) ||
		(player.x <= food4.x + food4.size && player.x + player.size >= food4.x + food4.size && 
			player.y <= food4.y + food4.size && player.y + player.size >= food4.y + food4.size)) {
			food4.eaten = 1;
		} 	

		if ((player.x <= food5.x && player.x + player.size >= food5.x && 
			 	player.y <= food5.y && player.y + player.size >= food5.y) ||
			(player.x <= food5.x && player.x + player.size >= food5.x && 
				player.y <= food5.y + food5.size && player.y + player.size >= food5.y + food5.size) ||
			(player.x <= food5.x + food5.size && player.x + player.size >= food5.x + food5.size && 
				player.y <= food5.y && player.y + player.size >= food5.y) ||
			(player.x <= food5.x + food5.size && player.x + player.size >= food5.x + food5.size && 
				player.y <= food5.y + food5.size && player.y + player.size >= food5.y + food5.size)) {
				food5.eaten = 1;
			} 	

		if ((player.x <= food6.x && player.x + player.size >= food6.x && 
			 	player.y <= food6.y && player.y + player.size >= food6.y) ||
			(player.x <= food6.x && player.x + player.size >= food6.x && 
				player.y <= food6.y + food6.size && player.y + player.size >= food6.y + food6.size) ||
			(player.x <= food6.x + food6.size && player.x + player.size >= food6.x + food6.size && 
				player.y <= food6.y && player.y + player.size >= food6.y) ||
			(player.x <= food6.x + food6.size && player.x + player.size >= food6.x + food6.size && 
				player.y <= food6.y + food6.size && player.y + player.size >= food6.y + food6.size)) {
				food6.eaten = 1;
			} 	

		if ((player.x <= food7.x && player.x + player.size >= food7.x && 
			 	player.y <= food7.y && player.y + player.size >= food7.y) ||
			(player.x <= food7.x && player.x + player.size >= food7.x && 
				player.y <= food7.y + food7.size && player.y + player.size >= food7.y + food7.size) ||
			(player.x <= food7.x + food7.size && player.x + player.size >= food7.x + food7.size && 
				player.y <= food7.y && player.y + player.size >= food7.y) ||
			(player.x <= food7.x + food7.size && player.x + player.size >= food7.x + food7.size && 
				player.y <= food7.y + food7.size && player.y + player.size >= food7.y + food7.size)) {
				food7.eaten = 1;
			} 	

		if ((player.x <= food8.x && player.x + player.size >= food8.x && 
			 	player.y <= food8.y && player.y + player.size >= food8.y) ||
			(player.x <= food8.x && player.x + player.size >= food8.x && 
				player.y <= food8.y + food8.size && player.y + player.size >= food8.y + food8.size) ||
			(player.x <= food8.x + food8.size && player.x + player.size >= food8.x + food8.size && 
				player.y <= food8.y && player.y + player.size >= food8.y) ||
			(player.x <= food8.x + food8.size && player.x + player.size >= food8.x + food8.size && 
				player.y <= food8.y + food8.size && player.y + player.size >= food8.y + food8.size)) {
				food8.eaten = 1;
			} 	

	}	

	
}

static void movePlayer() {

	if(KEY_DOWN_NOW(KEY_UP)) {
		player.y -= player.v;
	}
	if(KEY_DOWN_NOW(KEY_DOWN)) {
		player.y += player.v;
	}
	if(KEY_DOWN_NOW(KEY_LEFT)) {
		player.x -= player.v;
	}
	if(KEY_DOWN_NOW(KEY_RIGHT)) {
		player.x += player.v;
	}

}


static void moveEnemies() {

	if (enemy1.y <= danger_upper_bound) {
		enemy1.v = -enemy1.v;
		enemy1.y += enemy1.v;
	} else if(enemy1.y + enemy1.size + 1 >= danger_lower_bound){
		enemy1.y = 19;
		enemy1.v = -enemy1.v;
	} else {
		enemy1.y += enemy1.v;
	}
	
	drawRect(enemy1.x, enemy1.y, enemy1.size, enemy1.size*2, enemy1.color);
	drawRect(enemy1.x - 4, enemy1.y + 2, enemy1.size - 1, enemy1.size -1, enemy2.color);
	drawRect(enemy1.x + 5, enemy1.y + 2, enemy1.size -1, enemy1.size -1, enemy3.color);

	

	if (enemy2.y <= danger_upper_bound) {
		enemy2.v = -enemy2.v;
		enemy2.y += enemy2.v;
	} else if(enemy2.y + enemy2.size + 1 >= danger_lower_bound){
		enemy2.y = 19;
		enemy2.v = -enemy2.v;
	} else {
		enemy2.y += enemy2.v;
	}
	
	drawRect(enemy2.x, enemy2.y, enemy2.size, enemy2.size*2, enemy2.color);
	drawRect(enemy2.x - 4, enemy2.y + 2, enemy2.size - 1, enemy2.size -1, enemy2.color);
	drawRect(enemy2.x + 5, enemy2.y + 2, enemy2.size -1, enemy2.size -1, enemy2.color);

	if (enemy3.y <= danger_upper_bound) {
		enemy3.v = -enemy3.v;
		enemy3.y += enemy3.v;
	} else if(enemy3.y + enemy3.size + 1 >= danger_lower_bound){
		enemy3.y = 19;
		enemy3.v = -enemy3.v;
	} else {
		enemy3.y += enemy3.v;
	}
	
	drawRect(enemy3.x, enemy3.y, enemy3.size, enemy3.size*2, enemy3.color);
	drawRect(enemy3.x - 4, enemy3.y + 2, enemy3.size - 1, enemy3.size -1, enemy3.color);
	drawRect(enemy3.x + 5, enemy3.y + 2, enemy3.size -1, enemy3.size -1, enemy3.color);

	if (enemy4.y <= danger_upper_bound) {
		enemy4.v = -enemy4.v;
		enemy4.y += enemy4.v;
	} else if(enemy4.y + enemy4.size + 1 >= danger_lower_bound){
		enemy4.y = 19;
		enemy4.v = -enemy4.v;
	} else {
		enemy4.y += enemy4.v;
	}
	
	drawRect(enemy4.x, enemy4.y, enemy4.size, enemy4.size*2, enemy4.color);
	drawRect(enemy4.x - 4, enemy4.y + 2, enemy4.size - 1, enemy4.size -1, enemy4.color);
	drawRect(enemy4.x + 5, enemy4.y + 2, enemy4.size -1, enemy4.size -1, enemy4.color);

	if (enemy5.y <= danger_upper_bound) {
		enemy5.v = -enemy5.v;
		enemy5.y += enemy5.v;
	} else if(enemy5.y + enemy5.size + 1 >= danger_lower_bound){
		enemy5.y = 19;
		enemy5.v = -enemy5.v;
	} else {
		enemy5.y += enemy5.v;
	}
	
	drawRect(enemy5.x, enemy5.y, enemy5.size, enemy5.size*2, enemy5.color);
	drawRect(enemy5.x - 4, enemy5.y + 2, enemy5.size - 1, enemy5.size -1, enemy5.color);
	drawRect(enemy5.x + 5, enemy5.y + 2, enemy5.size -1, enemy5.size -1, enemy5.color);

	if (enemy6.y <= danger_upper_bound) {
		enemy6.v = -enemy6.v;
		enemy6.y += enemy6.v;
	} else if(enemy6.y + enemy6.size + 1 >= danger_lower_bound){
		enemy6.y = 19;
		enemy6.v = -enemy6.v;
	} else {
		enemy6.y += enemy6.v;
	}
	
	//drawRect(enemy6.x, enemy6.y, enemy6.size, enemy6.size, enemy6.color);
	drawRect(enemy6.x, enemy6.y, enemy6.size, enemy6.size*2, enemy6.color);
	drawRect(enemy6.x - 4, enemy6.y + 2, enemy6.size - 1, enemy6.size -1, enemy6.color);
	drawRect(enemy6.x + 5, enemy6.y + 2, enemy6.size -1, enemy6.size -1, enemy6.color);


	
}

static void defineBoundaries() {
	
	if(player.x < danger_left_bound) {
		player.x = prev_x;
		player.y = prev_y;
	}
	
	if(player.x + player.size > danger_right_bound) {
		player.x = prev_x;
		player.y = prev_y;
	}
	
	if(player.x > danger_left_bound && player.x + player.size < danger_right_bound) {
		//if(player.y < danger_upper_bound) {
		if(player.y < danger_upper_bound) {
			player.x = prev_x;
			player.y = prev_y;
		}
		if(player.y + player.size > danger_lower_bound) {
			player.x = prev_x;
			player.y = prev_y;
		}
	}
	
}
