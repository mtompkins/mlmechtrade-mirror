#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_ADOSC - Chaikin A/D Oscillator
 * Input Parameters
 * -------------------
 * high
 * low
 * close
 * volume
 * 
 * Optional Parameters
 * -------------------
 * optInFastPeriod:(From 2 to 100000) Description: Number of period for the fast MA
 * optInSlowPeriod:(From 2 to 100000) Description: Number of period for the slow MA
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
  double * volume;
/* optional input */
  int	 optInFastPeriod;
  int	 optInSlowPeriod;
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
  if (nrhs < 4 || nrhs > 6) mexErrMsgTxt("#6 inputs possible #2 optional.");
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
  /* Create a pointer to the input matrix volume. */
  volume = mxGetPr(prhs[3]);
  /* Get the dimensions of the matrix input volume. */
  inSeriesCols = mxGetN(prhs[3]);
  if (inSeriesCols != 1) mexErrMsgTxt("volume only vector alowed.");
  inSeriesRows = mxGetM(prhs[3]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInFastPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastPeriod = (int)  mxGetScalar(prhs[4]);
  } else {
  	optInFastPeriod = 3;
  }
  if (nrhs >= 5+1) {
	if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1) 
    	mexErrMsgTxt("Input optInSlowPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowPeriod = (int)  mxGetScalar(prhs[5]);
  } else {
  	optInSlowPeriod = 10;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_ADOSC(
                   startIdx, endIdx,
                   high,
                   low,
                   close,
                   volume,
                   optInFastPeriod,
                   optInSlowPeriod,
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
