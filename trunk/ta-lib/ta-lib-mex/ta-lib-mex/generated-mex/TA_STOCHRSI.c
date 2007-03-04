#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_STOCHRSI - Stochastic Relative Strength Index
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 2 to 100000) Description: Number of period
 * optInFastKPeriod:(From 1 to 100000) Description: Time period for building the Fast-K line
 * optInFastDPeriod:(From 1 to 100000) Description: Smoothing for making the Fast-D line. Usually set to 3
 * optInFastDMA:(From ${optInArg.Range.Minimum} to ${optInArg.Range.Maximum}) Description: Type of Moving Average for Fast-D
 *
 * Output
 * -------------------
 * outFastK
 * outFastD
 * 
 */
 
/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
/* ----------------- Variables ----------------- */
/* input variables */
/* mandatory input */
  int startIdx;
  int endIdx;
  double * inReal;
/* optional input */
  int	 optInTimePeriod;
  int	 optInFastKPeriod;
  int	 optInFastDPeriod;
  double	 optInFastDMA;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outFastK;
  double*	 outFastD;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 5) mexErrMsgTxt("#5 inputs possible #4 optional.");
  if (nlhs != 2) mexErrMsgTxt("#2 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix inReal. */
  inReal = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input inReal. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("inReal only vector alowed.");
  inSeriesRows = mxGetM(prhs[0]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 1+1) {
	if (!mxIsDouble(prhs[1]) || mxIsComplex(prhs[1]) ||
      mxGetN(prhs[1])*mxGetM(prhs[1]) != 1) 
    	mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInTimePeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInTimePeriod = 14;
  }
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInFastKPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastKPeriod = (int)  mxGetScalar(prhs[2]);
  } else {
  	optInFastKPeriod = 5;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInFastDPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastDPeriod = (int)  mxGetScalar(prhs[3]);
  } else {
  	optInFastDPeriod = 3;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInFastDMA must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastDMA =   mxGetScalar(prhs[4]);
  } else {
  	optInFastDMA = 0;
  }

/* ----------------- OUTPUT ----------------- */
  outFastK = mxCalloc(inSeriesRows, sizeof(double));
  outFastD = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_STOCHRSI(
                   startIdx, endIdx,
                   inReal,
                   optInTimePeriod,
                   optInFastKPeriod,
                   optInFastDPeriod,
                   optInFastDMA,
                   &outBegIdx, &outNbElement,
                   outFastK,                   outFastD);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outFastK);
   	   mxFree(outFastD);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outFastK, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outFastK);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outFastD, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outFastD);  
} /* END mexFunction */
