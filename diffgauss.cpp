#include<differenceofgaussian.h>
#include<android/log.h>

DifferenceOfGaussianFunc::DifferenceOfGaussianFunc(double in_sigma,double in_mu){
	sigma = in_sigma;
	mu = in_mu;
	PI = 3.14159;
}

void DifferenceOfGaussianFunc::getWeight(double* out_gf,double* out_gf_k){
    double k=pow(2, 1.0/3.0);
    k=pow(2, 1.0/2.0);
    //__android_log_print(ANDROID_LOG_ERROR,"k","%f",k);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_gf[(i+1)+(j+1)*3]  =(1/(2*PI*(sigma*sigma)))     * exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_gf_k[(i+1)+(j+1)*3]=(1/(2*PI*(k*sigma*k*sigma))) * exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}

void DifferenceOfGaussianFunc::getdxxWeight(double* out_dxxgf,double* out_dxxgf_k){
    double k=pow(2, 1.0/3.0);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dxxgf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,6)))   *(i*i-sigma*sigma)    *exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dxxgf_k[(i+1)+(j+1)*3]=(1/(2*PI*pow(k*sigma,6))) *(i*i-k*sigma*k*sigma)*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}

void DifferenceOfGaussianFunc::getdxyWeight(double* out_dxygf,double* out_dxygf_k){
    double k=pow(2, 1.0/3.0);//1.2599

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dxygf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,6)))   *(i*j)*exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dxygf_k[(i+1)+(j+1)*3]=(1/(2*PI*pow(k*sigma,6))) *(i*j)*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}

void DifferenceOfGaussianFunc::getdyyWeight(double* out_dyygf,double* out_dyygf_k){
    double k=pow(2, 1.0/3.0);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dyygf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,6)))   *(j*j-sigma*sigma)    *exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dyygf_k[(i+1)+(j+1)*3]=(1/(2*PI*pow(k*sigma,6))) *(j*j-k*sigma*k*sigma)*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}

void DifferenceOfGaussianFunc::getdxsWeight(double* out_dxsgf,double* out_dxsgf_k){
	double k=pow(2, 1.0/3.0);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dxsgf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,7)))   *(4*sigma*sigma-(i*i+j*j)*i)    *exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dxsgf_k[(i+1)+(j+1)*3]=(1/(2*PI*pow(k*sigma,7))) *(4*k*sigma*k*sigma-(i*i+j*j)*i)*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}
void DifferenceOfGaussianFunc::getdysWeight(double* out_dysgf,double* out_dysgf_k){
	double k=pow(2, 1.0/3.0);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dysgf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,7)))   *(4*sigma*sigma-(i*i+j*j)*j)    *exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dysgf_k[(i+1)+(j+1)*3]=(1/(2*PI*pow(k*sigma,7))) *(4*k*sigma*k*sigma-(i*i+j*j)*j)*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}
/*dx*ds=ds*dx��*/
void DifferenceOfGaussianFunc::getdssWeight(double* out_dssgf,double* out_dssgf_k){
	double k=pow(2, 1.0/3.0);

    for(int j=-1;j<=1;j++){
    	for(int i=-1;i<=1;i++){
    		out_dssgf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(sigma,8)))  *(6*pow(sigma,4)-7*sigma*sigma*(i*i+j*j)+pow((i*i+j*j),2))      *exp(-1*(i*i+j*j)/(2*(sigma*sigma)));
    		out_dssgf[(i+1)+(j+1)*3]  =(1/(2*PI*pow(k*sigma,8)))*(6*pow(k*sigma,4)-7*k*sigma*k*sigma*(i*i+j*j)+pow((i*i+j*j),2))*exp(-1*(i*i+j*j)/(2*(k*sigma*k*sigma)));
    	}
    }
}


int DifferenceOfGaussianFunc::edgeReduce(double xx, double xy, double yy){
	double traceHessian = xx+yy;
	double detHessian = xx*yy+xy*xy;

	if((traceHessian*traceHessian)/detHessian < (11^2)/10){
		//__android_log_print(ANDROID_LOG_ERROR,"k","%f",(traceHessian*traceHessian)/detHessian);
		return 0;
	}else{
		return -1;
	}
}
