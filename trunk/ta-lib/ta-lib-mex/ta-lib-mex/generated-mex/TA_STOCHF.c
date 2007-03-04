#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_STOCHF - Stochastic Fast
 * Input Parameters
 * -------------------
 * high
 * low
 * close
 * 
 * Optional Parameters
 * -------------------
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
  double * high;
  double * low;
  double * close;
/* optional input */
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
  if (nrhs < 3 || nrhs > 6) mexErrMsgTxt("#6 inputs possible #3 optional.");
  if (nlhs != 2) mexErrMsgTxt("#2 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix high. */
  high = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input high. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("high only vector alowed.");
  /* Create a pointer to the input matrix low. */
  low = mxGetPr(prhs[1]);
  /* Get the dimensions of the matrix input low. */
  inSeriesCols = mxGetN(prhs[1]);
  if (inSeriesCols != 1) mexErrMsgTxt("low only vector alowed.");
  /* Create a pointer to the input matrix close. */
  close = mxGetPr(prhs[2]);
  /* Get the dimensions of the matrix input close. */
  inSeriesCols = mxGetN(prhs[2]);
  if (inSeriesCols != 1) mexErrMsgTxt("close only vector alowed.");
  inSeriesRows = mxGetM(prhs[2]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInFastKPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastKPeriod = (int)  mxGetScalar(prhs[3]);
  } else {
  	optInFastKPeriod = 5;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInFastDPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastDPeriod = (int)  mxGetScalar(prhs[4]);
  } else {
  	optInFastDPeriod = 3;
  }
  if (nrhs >= 5+1) {
	if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1) 
    	mexErrMsgTxt("Input optInFastDMA must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastDMA =   mxGetScalar(prhs[5]);
  } else {
  	optInFastDMA = 0;
  }

/* ----------------- OUTPUT ----------------- */
  outFastK = mxCalloc(inSeriesRows, sizeof(double));
  outFastD = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_STOCHF(
                   startIdx, endIdx,
                   high,
                   low,
                   close,
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
