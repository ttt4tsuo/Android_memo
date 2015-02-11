#include<photokuri.h>
#include<differenceofgaussian.h>

void graydone(AndroidBitmapInfo infocolor, void* pixelscolor, AndroidBitmapInfo infogray, void* pixelsgray);
void gaussian(DifferenceOfGaussianFunc* differenceOfGaussianFunc, AndroidBitmapInfo infogray, void* pixelsgray);
int g(DifferenceOfGaussianFunc* differenceOfGaussianFunc, AndroidBitmapInfo infogray, void* pixelsgray,int x, int y);
void make_orientation(AndroidBitmapInfo infolaplac,void* pixelslaplac,std::multimap<int, int> cntr);
void find_maximal(int x,int y, void* pixelsgray, void* pixelsover, void* pixelsunder, AndroidBitmapInfo infogray);
std::multimap<int, int> cntr;
char * lenamap;

extern "C" {
	JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_clearGray(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray,jobject bitmapover,jobject bitmapunder,jobject bitmaplaplac,double sgm);
}

JNIEXPORT void JNICALL Java_jp_kanagawa_kawasaki_photokuri_PhotoKuri_clearGray(JNIEnv * env, jobject  obj, jobject bitmapcolor,jobject bitmapgray,jobject bitmapover,jobject bitmapunder,jobject bitmaplaplac,double sgm)
{
    AndroidBitmapInfo  infocolor;
    void*              pixelscolor;
    void*              save_pixelscolor;
    AndroidBitmapInfo  infogray;
    void*              pixelsgray;
    void*              save_pixelsgray;
    AndroidBitmapInfo  infoover;
    void*              pixelsover;
    void*              save_pixelsover;
    AndroidBitmapInfo  infounder;
    void*              pixelsunder;
    void*              save_pixelsunder;
    AndroidBitmapInfo  infolaplac;
    void*              pixelslaplac;
    void*              save_pixelslaplac;
    int                ret;

    if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
        return;
    }
    if ((ret = AndroidBitmap_getInfo(env, bitmapgray, &infogray)) < 0) {
        return;
    }
    if ((ret = AndroidBitmap_getInfo(env, bitmapover, &infoover)) < 0) {
        return;
    }
    if ((ret = AndroidBitmap_getInfo(env, bitmapunder, &infounder)) < 0) {
        return;
    }
    if ((ret = AndroidBitmap_getInfo(env, bitmaplaplac, &infolaplac)) < 0) {
        return;
    }
    if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
    	return;
    }
    if (infogray.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }
    if (infoover.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }
    if (infounder.format != ANDROID_BITMAP_FORMAT_A_8) {
        return;
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor)) < 0) {
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray)) < 0) {
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmapover, &pixelsover)) < 0) {
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmapunder, &pixelsunder)) < 0) {
    }
    if ((ret = AndroidBitmap_lockPixels(env, bitmaplaplac, &pixelslaplac)) < 0) {
    }

    //Save
    save_pixelscolor=pixelscolor;
    save_pixelsgray=pixelsgray;
    save_pixelsover=pixelsover;
    save_pixelsunder=pixelsunder;
    save_pixelslaplac=pixelslaplac;

	//pixelscolor=save_pixelscolor;
	graydone(infocolor, pixelscolor, infogray, pixelsgray);
	pixelscolor=save_pixelscolor;
	graydone(infocolor, pixelscolor, infoover, pixelsover);
	pixelscolor=save_pixelscolor;
	graydone(infocolor, pixelscolor, infounder, pixelsunder);
    pixelscolor=save_pixelscolor;
    graydone(infocolor, pixelscolor, infolaplac, pixelslaplac);
    pixelscolor=save_pixelscolor;
    AndroidBitmap_unlockPixels(env, bitmapcolor);

    /*http://imagingsolution.blog107.fc2.com/blog-entry-165.html���sigma��0.8���̗p*/
	double sigma=sgm;
	DifferenceOfGaussianFunc *differenceOfGaussianFunc;
	differenceOfGaussianFunc = new DifferenceOfGaussianFunc(sigma,1);
	pixelsgray=save_pixelsgray;
	gaussian(differenceOfGaussianFunc,infogray,pixelsgray);
	/*Over*/
	sigma=sgm+0.2;
	DifferenceOfGaussianFunc *differenceOfGaussianFuncOver;
	differenceOfGaussianFuncOver = new DifferenceOfGaussianFunc(sigma,1);
	pixelsover=save_pixelsover;
	gaussian(differenceOfGaussianFuncOver,infoover,pixelsover);
	/*Under*/
	sigma=sgm-0.2;
	DifferenceOfGaussianFunc *differenceOfGaussianFuncUnder;
	differenceOfGaussianFuncUnder = new DifferenceOfGaussianFunc(sigma,1);
	pixelsunder=save_pixelsunder;
	gaussian(differenceOfGaussianFuncUnder,infounder,pixelsunder);
	/*Laplac*/
	sigma=sgm;
	DifferenceOfGaussianFunc *differenceOfGaussianFuncLaplac;
	differenceOfGaussianFuncLaplac = new DifferenceOfGaussianFunc(sigma,1);
	pixelslaplac=save_pixelslaplac;
	gaussian(differenceOfGaussianFuncLaplac,infolaplac,pixelslaplac);

    lenamap = (char *)malloc(infogray.height * infogray.width);
    memset(lenamap,'x',infogray.height * infogray.width);
    cntr.clear();
    pixelsgray=save_pixelsgray;

    for (int y=1;y<infogray.height-1;y++) {
        for (int x=1;x<infogray.width-1;x++) {
        	if(strncmp(lenamap+(infogray.width*y)+x, "o", 1) != 0){
        		strncpy(lenamap+(infogray.width*y)+x,"o",1);
        		find_maximal(x,y,pixelsgray,pixelsover,pixelsunder,infogray);
        	}
        }
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }

	/* test */
    pixelsgray=save_pixelsgray;
    std::multimap<int,int>::iterator it = cntr.begin();
    for (int y=1;y<infogray.height-1;y++) {
    	uint8_t * grayline = (uint8_t *) pixelsgray;
    	/*for(int x=1;x<infogray.width-1;x++){
    		grayline[x]=255;
    	}*/
    	//g(differenceOfGaussianFuncUnder,infounder,pixelsunder,(*it).first,(*it).second);
	    	if(y==(*it).first){
		    	while(y==(*it).first){
		    		if(0==g(differenceOfGaussianFuncUnder,infounder,pixelsunder,(*it).first,(*it).second)){
		    			grayline[(*it).second]=255;
		    		}else{
		    			grayline[(*it).second]=0;
		    		}
		    		it++;
		    	}
			}else{
				++it;
			}


		pixelsgray = (char *) pixelsgray + infogray.stride;
    }
    make_orientation(infolaplac,pixelslaplac,cntr);

    AndroidBitmap_unlockPixels(env, bitmapgray);
    AndroidBitmap_unlockPixels(env, bitmapover);
    AndroidBitmap_unlockPixels(env, bitmapunder);
    //free(lenamap);������
}

void graydone(AndroidBitmapInfo infocolor, void* pixelscolor, AndroidBitmapInfo infogray, void* pixelsgray){
    for (int y=0;y<infocolor.height;y++) {
        argb * line = (argb *) pixelscolor;
        uint8_t * grayline = (uint8_t *) pixelsgray;
        for (int x=0;x<infocolor.width;x++) {
            grayline[x]=255-(0.3*line[x].red+0.59*line[x].green+0.11*line[x].blue);
        }
        pixelscolor = (char *)pixelscolor + infocolor.stride;
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }
}

void gaussian(DifferenceOfGaussianFunc* differenceOfGaussianFunc, AndroidBitmapInfo infogray, void* pixelsgray){

    double gf[9];/*exp�̌v�Z�́A���ׂ�double�^�ōs��Ȃ��Ɗۂ߂��������ĕs����ł�*/
    double gf_k[9];
	differenceOfGaussianFunc->getWeight(gf,gf_k);

    for (int y=1;y<infogray.height-1;y++) {
    	uint8_t * grayline = (uint8_t *) pixelsgray;
        for (int x=1;x<infogray.width-1;x++) {
				int cn =(grayline[x]);
				int rg =(grayline[x+1]);
				int lf =(grayline[x-1]);
				int up =(grayline[x-infogray.width]);
				int dw =(grayline[x+infogray.width]);
				int upr = (grayline[x-infogray.width+1]);
				int upl = (grayline[x-infogray.width-1]);
				int dwr = (grayline[x+infogray.width+1]);
				int dwl = (grayline[x+infogray.width-1]);
				/*�������ŏ㏑����������Ă�*/
				grayline[x]=-(gf_k[0]*upl+gf_k[1]*up+gf_k[2]*upr + gf_k[3]*lf+gf_k[4]*cn+gf_k[5]*rg + gf_k[6]*dwl+gf_k[7]*dw+gf_k[8]*dwr)
							+(gf[0]*upl  +gf[1]*up  +gf[2]*upr   + gf[3]*lf  +gf[4]*cn  +gf[5]*rg   + gf[6]*dwl  +gf[7]*dw  +gf[8]*dwr);

        }
        pixelsgray = (char *) pixelsgray + infogray.stride;
    }
}

int g(DifferenceOfGaussianFunc* differenceOfGaussianFunc, AndroidBitmapInfo infogray, void* pixelsgray,int x, int y){

    double dxxgf[9],dxxgf_k[9];
    double dxygf[9],dxygf_k[9];
    double dyygf[9],dyygf_k[9];
	differenceOfGaussianFunc->getdxxWeight(dxxgf,dxxgf_k);
	differenceOfGaussianFunc->getdxyWeight(dxygf,dxygf_k);
	differenceOfGaussianFunc->getdyyWeight(dyygf,dyygf_k);

    pixelsgray = (char *) pixelsgray + (infogray.stride * y);
    uint8_t * grayline = (uint8_t *) pixelsgray;

	int cn =(grayline[x]);
	int rg =(grayline[x+1]);
	int lf =(grayline[x-1]);
	int up =(grayline[x-infogray.width]);
	int dw =(grayline[x+infogray.width]);
	int upr = (grayline[x-infogray.width+1]);
	int upl = (grayline[x-infogray.width-1]);
	int dwr = (grayline[x+infogray.width+1]);
	int dwl = (grayline[x+infogray.width-1]);

	double xx=-(dxxgf_k[0]*upl+dxxgf_k[1]*up+dxxgf_k[2]*upr + dxxgf_k[3]*lf+dxxgf_k[4]*cn+dxxgf_k[5]*rg + dxxgf_k[6]*dwl+dxxgf_k[7]*dw+dxxgf_k[8]*dwr)
				+(dxxgf[0]*upl  +dxxgf[1]*up  +dxxgf[2]*upr   + dxxgf[3]*lf  +dxxgf[4]*cn  +dxxgf[5]*rg   + dxxgf[6]*dwl  +dxxgf[7]*dw  +dxxgf[8]*dwr);
	double xy=-(dxygf_k[0]*upl+dxygf_k[1]*up+dxygf_k[2]*upr + dxygf_k[3]*lf+dxygf_k[4]*cn+dxygf_k[5]*rg + dxygf_k[6]*dwl+dxygf_k[7]*dw+dxygf_k[8]*dwr)
				+(dxygf[0]*upl  +dxygf[1]*up  +dxygf[2]*upr   + dxygf[3]*lf  +dxygf[4]*cn  +dxygf[5]*rg   + dxygf[6]*dwl  +dxygf[7]*dw  +dxygf[8]*dwr);
	double yy=-(dyygf_k[0]*upl+dyygf_k[1]*up+dyygf_k[2]*upr + dyygf_k[3]*lf+dyygf_k[4]*cn+dyygf_k[5]*rg + dyygf_k[6]*dwl+dyygf_k[7]*dw+dyygf_k[8]*dwr)
				+(dyygf[0]*upl  +dyygf[1]*up  +dyygf[2]*upr   + dyygf[3]*lf  +dyygf[4]*cn  +dyygf[5]*rg   + dyygf[6]*dwl  +dyygf[7]*dw  +dyygf[8]*dwr);

	return differenceOfGaussianFunc->edgeReduce(xx,xy,yy);
}

void make_orientation(AndroidBitmapInfo infolaplac,void* pixelslaplac,std::multimap<int, int> cntr){
	std::multimap<int,int>::iterator it = cntr.begin();
	for (int y=1;y<infolaplac.height-1;y++) {
    	uint8_t * grayline = (uint8_t *) pixelslaplac;
	    if(y==(*it).first){
			for(int x=1;x<infolaplac.width-1;x++){
		    	if(x==(*it).second){
		    		/*����*/
		    		double fu=grayline[x+1]-grayline[x-1];
		    		double fv=grayline[x+infolaplac.width]-grayline[x-infolaplac.width];
		    		double m=sqrt(fu*fu+fv*fv);
		    		double tht=1/(tan(fv/fu));
		    		__android_log_print(ANDROID_LOG_ERROR,"orientation","%f %f",m,tht);
		    	}
		    	++it;
		    }
		}else{
			++it;
		}
		pixelslaplac = (char *) pixelslaplac + infolaplac.stride;
    }
}

void find_maximal(int x,int y, void* pixelsgray, void* pixelsover, void* pixelsunder, AndroidBitmapInfo infogray){

	int maxpoint=0;
	int xpoint=0;
	int ypoint=0;

	uint8_t * grayline = (uint8_t *) pixelsgray;

	int cn =(grayline[x]);
	int rg =(grayline[x+1]);
	int lf =(grayline[x-1]);
	int up =(grayline[x-infogray.width]);
	int dw =(grayline[x+infogray.width]);
	int upr = (grayline[x-infogray.width+1]);
	int upl = (grayline[x-infogray.width-1]);
	int dwr = (grayline[x+infogray.width+1]);
	int dwl = (grayline[x+infogray.width-1]);
	maxpoint=max(cn,max(rg,max(lf,max(up,max(dw,max(upr,max(upl,max(dwr,dwl))))))));
	if(maxpoint==dwl){
		xpoint=x-1;
		ypoint=y+1;
		pixelsgray = (char *) pixelsgray + infogray.stride;
		pixelsover = (char *) pixelsover + infogray.stride;
		pixelsunder = (char *) pixelsunder + infogray.stride;
	}else if(maxpoint==dwr){
		xpoint=x+1;
		ypoint=y+1;
		pixelsgray = (char *) pixelsgray + infogray.stride;
		pixelsover = (char *) pixelsover + infogray.stride;
		pixelsunder = (char *) pixelsunder + infogray.stride;
	}else if(maxpoint==upl){
		xpoint=x-1;
		ypoint=y-1;
		pixelsgray = (char *) pixelsgray - infogray.stride;
		pixelsover = (char *) pixelsover - infogray.stride;
		pixelsunder = (char *) pixelsunder - infogray.stride;
	}else if(maxpoint==upr){
		xpoint=x+1;
		ypoint=y-1;
		pixelsgray = (char *) pixelsgray - infogray.stride;
		pixelsover = (char *) pixelsover - infogray.stride;
		pixelsunder = (char *) pixelsunder - infogray.stride;
	}else if(maxpoint==dw){
		xpoint=x;
		ypoint=y+1;
		pixelsgray = (char *) pixelsgray + infogray.stride;
		pixelsover = (char *) pixelsover + infogray.stride;
		pixelsunder = (char *) pixelsunder + infogray.stride;
	}else if(maxpoint==up){
		xpoint=x;
		ypoint=y-1;
		pixelsgray = (char *) pixelsgray - infogray.stride;
		pixelsover = (char *) pixelsover - infogray.stride;
		pixelsunder = (char *) pixelsunder - infogray.stride;
	}else if(maxpoint==lf){
		xpoint=x-1;
		ypoint=y;
	}else if(maxpoint==rg){
		xpoint=x+1;
		ypoint=y;
	}else if(maxpoint==cn){
		xpoint=x;
		ypoint=y;
	}

	if(maxpoint == cn){
		strncpy(lenamap+(infogray.width*ypoint-1)+xpoint-1,"ooo",3);
		strncpy(lenamap+(infogray.width*ypoint)+xpoint-1,"ooo",3);
		strncpy(lenamap+(infogray.width*ypoint+1)+xpoint-1,"ooo",3);

		uint8_t * overline = (uint8_t *) pixelsover;
		cn =(overline[x]);
		rg =(overline[x+1]);
		lf =(overline[x-1]);
		up =(overline[x-infogray.width]);
		dw =(overline[x+infogray.width]);
		upr = (overline[x-infogray.width+1]);
		upl = (overline[x-infogray.width-1]);
		dwr = (overline[x+infogray.width+1]);
		dwl = (overline[x+infogray.width-1]);
		if(maxpoint<max(cn,max(rg,max(lf,max(up,max(dw,max(upr,max(upl,max(dwr,dwl))))))))){
			return;
		}

		uint8_t * underline = (uint8_t *) pixelsunder;
		cn =(underline[x]);
		rg =(underline[x+1]);
		lf =(underline[x-1]);
		up =(underline[x-infogray.width]);
		dw =(underline[x+infogray.width]);
		upr = (underline[x-infogray.width+1]);
		upl = (underline[x-infogray.width-1]);
		dwr = (underline[x+infogray.width+1]);
		dwl = (underline[x+infogray.width-1]);
		if(maxpoint<max(cn,max(rg,max(lf,max(up,max(dw,max(upr,max(upl,max(dwr,dwl))))))))){
			return;
		}
		cntr.insert(std::make_pair(y,x));
	}else if(xpoint<1 || xpoint>=infogray.width-1 || ypoint<1 || ypoint>=infogray.height-1){
		strncpy(lenamap+(infogray.width*ypoint-1)+xpoint-1,"ooo",3);
		strncpy(lenamap+(infogray.width*ypoint)+xpoint-1,"ooo",3);
		strncpy(lenamap+(infogray.width*ypoint+1)+xpoint-1,"ooo",3);
		return;
	}else{
		if(strncmp(lenamap+(infogray.width*ypoint)+xpoint, "o", 1) != 0){
			strncpy(lenamap+(infogray.width*ypoint-1)+xpoint-1,"ooo",3);
			strncpy(lenamap+(infogray.width*ypoint)+xpoint-1,"ooo",3);
			strncpy(lenamap+(infogray.width*ypoint+1)+xpoint-1,"ooo",3);
			find_maximal(xpoint,ypoint,pixelsgray,pixelsover,pixelsunder,infogray);
			if(y>ypoint){
				pixelsgray = (char *) pixelsgray + infogray.stride;
				pixelsover = (char *) pixelsover + infogray.stride;
				pixelsunder = (char *) pixelsunder + infogray.stride;
			}else if(y<ypoint){
				pixelsgray = (char *) pixelsgray - infogray.stride;
				pixelsover = (char *) pixelsover - infogray.stride;
				pixelsunder = (char *) pixelsunder - infogray.stride;
			}
		}
	}
}
