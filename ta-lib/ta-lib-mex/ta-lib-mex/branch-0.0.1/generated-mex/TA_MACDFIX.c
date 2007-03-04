#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_MACDFIX - Moving Average Convergence/Divergence Fix 12/26
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
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
  if (nrhs < 1 || nrhs > 2) mexErrMsgTxt("#2 inputs possible #1 optional.");
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
    	mexErrMsgTxt("Input optInSignalPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSignalPeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInSignalPeriod = 9;
  }

/* ----------------- OUTPUT ----------------- */
  outMACD = mxCalloc(inSeriesRows, sizeof(double));
  outMACDSignal = mxCalloc(inSeriesRows, sizeof(double));
  outMACDHist = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_MACDFIX(
                   startIdx, endIdx,
                   inReal,
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
