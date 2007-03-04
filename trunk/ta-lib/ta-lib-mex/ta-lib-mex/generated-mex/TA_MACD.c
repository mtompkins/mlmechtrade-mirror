#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_MACD - Moving Average Convergence/Divergence
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInFastPeriod:(From 2 to 100000) Description: Number of period for the fast MA
 * optInSlowPeriod:(From 2 to 100000) Description: Number of period for the slow MA
 * optInSignalPeriod:(From 1 to 100000) Description: Smoothing for the signal line (nb of period)
 *
 * Output
 * -------------------
 * outMACD
 * outMACDSignal
 * outMACDHist
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
  int	 optInFastPeriod;
  int	 optInSlowPeriod;
  int	 optInSignalPeriod;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outMACD;
  double*	 outMACDSignal;
  double*	 outMACDHist;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 4) mexErrMsgTxt("#4 inputs possible #3 optional.");
  if (nlhs != 3) mexErrMsgTxt("#3 output required.");
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
    	mexErrMsgTxt("Input optInFastPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastPeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInFastPeriod = 12;
  }
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInSlowPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowPeriod = (int)  mxGetScalar(prhs[2]);
  } else {
  	optInSlowPeriod = 26;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInSignalPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSignalPeriod = (int)  mxGetScalar(prhs[3]);
  } else {
  	optInSignalPeriod = 9;
  }

/* ----------------- OUTPUT ----------------- */
  outMACD = mxCalloc(inSeriesRows, sizeof(double));
  outMACDSignal = mxCalloc(inSeriesRows, sizeof(double));
  outMACDHist = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_MACD(
                   startIdx, endIdx,
                   inReal,
                   optInFastPeriod,
                   optInSlowPeriod,
                   optInSignalPeriod,
                   &outBegIdx, &outNbElement,
                   outMACD,                   outMACDSignal,                   outMACDHist);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outMACD);
   	   mxFree(outMACDSignal);
   	   mxFree(outMACDHist);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outMACD, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outMACD);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outMACDSignal, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outMACDSignal);  
  plhs[2] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[2]))+outBegIdx, outMACDHist, outNbElement*mxGetElementSize(plhs[2]));
  mxFree(outMACDHist);  
} /* END mexFunction */
