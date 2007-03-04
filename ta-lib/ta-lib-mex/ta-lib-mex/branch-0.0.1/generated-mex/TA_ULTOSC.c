#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_ULTOSC - Ultimate Oscillator
 * Input Parameters
 * -------------------
 * high
 * low
 * close
 * 
 * Optional Parameters
 * -------------------
 * optInFirstPeriod:(From 1 to 100000) Description: Number of bars for 1st period.
 * optInSecondPeriod:(From 1 to 100000) Description: Number of bars fro 2nd period
 * optInThirdPeriod:(From 1 to 100000) Description: Number of bars for 3rd period
 *
 * Output
 * -------------------
 * outReal
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
  int	 optInFirstPeriod;
  int	 optInSecondPeriod;
  int	 optInThirdPeriod;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outReal;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 3 || nrhs > 6) mexErrMsgTxt("#6 inputs possible #3 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
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
    	mexErrMsgTxt("Input optInFirstPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFirstPeriod = (int)  mxGetScalar(prhs[3]);
  } else {
  	optInFirstPeriod = 7;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInSecondPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSecondPeriod = (int)  mxGetScalar(prhs[4]);
  } else {
  	optInSecondPeriod = 14;
  }
  if (nrhs >= 5+1) {
	if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1) 
    	mexErrMsgTxt("Input optInThirdPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInThirdPeriod = (int)  mxGetScalar(prhs[5]);
  } else {
  	optInThirdPeriod = 28;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_ULTOSC(
                   startIdx, endIdx,
                   high,
                   low,
                   close,
                   optInFirstPeriod,
                   optInSecondPeriod,
                   optInThirdPeriod,
                   &outBegIdx, &outNbElement,
                   outReal);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outReal);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outReal, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outReal);  
} /* END mexFunction */
