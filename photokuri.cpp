#include<photokuri.h>

extern "C" {
	JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_convertToGray(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray);
	JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_genNoise(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray);
}

int kuriflag=0;

JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_convertToGray(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray)
{
    AndroidBitmapInfo  infocolor;
    void*              pixelscolor;
    AndroidBitmapInfo  infogray;
    void*              pixelsgray;
    int                ret;
    int                y;
    int                x;

    if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
        return;
    }

    if ((ret = AndroidBitmap_getInfo(env, bitmapgray, &infogray)) < 0) {
        return;
    }

    if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
    	return;
    }

    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor)) < 0) {
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray)) < 0) {
    }

    // modify pixels with image processing algorithm
    for (y=0;y<infocolor.height;y++) {
        argb * line = (argb *) pixelscolor;
        uint8_t * grayline = (uint8_t *) pixelsgray;
        for (x=0;x<infocolor.width;x++) {
        	/*if(0.3 * line[x].red + 0.59 * line[x].green + 0.11*line[x].blue>100){
        		grayline[x]=0;
        	}else{
            	grayline[x]=255;
            }*/
            grayline[x]=255-(0.3*line[x].red+0.59*line[x].green+0.11*line[x].blue);
        }
        pixelscolor = (char *)pixelscolor + infocolor.stride;
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }
    AndroidBitmap_unlockPixels(env, bitmapcolor);
    AndroidBitmap_unlockPixels(env, bitmapgray);
}



JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_genNoise(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray){
    AndroidBitmapInfo  infocolor;
    void*              pixelscolor;
    void*              save_pixelscolor;
    AndroidBitmapInfo  infogray;
    void*              pixelsgray;
    void*              save_pixelsgray;
    int                ret;
    int                y;
    int                x;

    if(kuriflag==0){
    	kuriflag=1;
    	if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
    		return;
    	}
    	if ((ret = AndroidBitmap_getInfo(env, bitmapgray, &infogray)) < 0) {
    		return;
    	}
    	if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
    		return;
    	}
    	if (infogray.format != ANDROID_BITMAP_FORMAT_A_8) {
    		return;
    	}
    	if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor)) < 0) {
    	}
    	if ((ret = AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray)) < 0) {
    	}

    	//Save
    	save_pixelscolor=pixelscolor;
    	save_pixelsgray=pixelsgray;

    	// modify pixels with image processing algorithm
    	for (y=0;y<infocolor.height;y++) {
    		argb * line = (argb *) pixelscolor;
    		uint8_t * grayline = (uint8_t *) pixelsgray;
    		for (x=0;x<infocolor.width;x++) {
    			/*if(0.3 * line[x].red + 0.59 * line[x].green + 0.11*line[x].blue>100){
        			grayline[x]=0;
        		}else{
            		grayline[x]=255;
            	}*/
    			grayline[x]=255-(0.3*line[x].red+0.59*line[x].green+0.11*line[x].blue);
    		}
    		pixelscolor = (char *)pixelscolor + infocolor.stride;
    		pixelsgray = (char *) pixelsgray + infogray.stride;
    	}
    	AndroidBitmap_unlockPixels(env, bitmapcolor);
    	AndroidBitmap_unlockPixels(env, bitmapgray);
    }

    if ((ret = AndroidBitmap_getInfo(env, bitmapgray, &infogray)) < 0) {
        return;
    }
    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray)) < 0) {
    }
    save_pixelscolor=pixelscolor;
    save_pixelsgray=pixelscolor;

    int i=0;
    int j=0;
    double PI=3.14159;
    double sigma=1.0;/*http://imagingsolution.blog107.fc2.com/blog-entry-165.html���0.8���̗p*/
    double x_i=0;
    double y_j=0;
    double gf[9];/*exp�̌v�Z�́A���ׂ�double�^�ōs��Ȃ��Ɗۂ߂��������ĕs����ł�*/

    for(i=-1;i<=1;i++){
    	for(j=-1;j<=1;j++){
    		x_i=(double)i;
    		y_j=(double)j;
    		gf[(i+1)*3+(j+1)]=(1/(2*PI*(sigma*sigma))) * exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    	}
    }

    // modify pixels with image processing algorithm

    for (y=1;y<infogray.height-1;y++) {
    	uint8_t * grayline = (uint8_t *) pixelsgray;
        for (x=1;x<infogray.width-1;x++) {
            	/*int cn =(2 & grayline[x])-1;
				int rg =(2 & grayline[x+1])-1;
				int lf =(2 & grayline[x-1])-1;
				int up =(2 & grayline[x-infogray.width])-1;
				int dw =(2 & grayline[x+infogray.width])-1;
				int upr = (2 & grayline[x-infogray.width+1])-1;
				int upl = (2 & grayline[x-infogray.width-1])-1;
				int dwr = (2 & grayline[x+infogray.width+1])-1;
				int dwl = (2 & grayline[x+infogray.width-1])-1;

				if( (-1.0)*(rg+lf+up+dw + upr+upl+dwr+dwl)-(2.1)*(cn)-( (-1.0)*(-rg-lf-up-dw - upr-upl-dwr-dwl)-(2.1)*(-cn) ) >0){
					grayline[x]=0;//1�ɐݒ肷��ƃG�l���M�[����A�G�l���M�[��Ⴍ����̂�-1�̍��ɐݒ�
				}else{
					grayline[x]=255;
				}*/
				int cn =(grayline[x]);
				int rg =(grayline[x+1]);
				int lf =(grayline[x-1]);
				int up =(grayline[x-infogray.width]);
				int dw =(grayline[x+infogray.width]);
				int upr = (grayline[x-infogray.width+1]);
				int upl = (grayline[x-infogray.width-1]);
				int dwr = (grayline[x+infogray.width+1]);
				int dwl = (grayline[x+infogray.width-1]);

				grayline[x]=(gf[0]*upl+gf[1]*up+gf[2]*upr + gf[3]*lf+gf[4]*cn+gf[5]*rg + gf[6]*dwl+gf[7]*dw+gf[8]*dwr);

        }
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }
    AndroidBitmap_unlockPixels(env, bitmapgray);
}


/*
JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_genNoise(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray){
    AndroidBitmapInfo  infogray;
    void*              pixelsgray;
    int                ret;
    int                y;
    int                x;

    if ((ret = AndroidBitmap_getInfo(env, bitmapgray, &infogray)) < 0) {
        return;
    }
    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray)) < 0) {
    }

    // modify pixels with image processing algorithm
    srand((unsigned)time(NULL));
    for (y=1;y<infogray.height-1;y++) {
        uint8_t * grayline = (uint8_t *) pixelsgray;
        for (x=1;x<infogray.width-1;x++) {
				if(rand()<RAND_MAX/100){
					grayline[x]=255^grayline[x];
				}
        }
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }
    AndroidBitmap_unlockPixels(env, bitmapgray);
}
*/
