#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_SAREXT - Parabolic SAR - Extended
 * Input Parameters
 * -------------------
 * high
 * low
 * 
 * Optional Parameters
 * -------------------
 * optInStartValue:(From -3.000000e+37 to 3.000000e+37) Description: Start value and direction. 0 for Auto, >0 for Long, <0 for Short
 * optInOffsetonReverse:(From 0.000000e+0 to 3.000000e+37) Description: Percent offset added/removed to initial stop on short/long reversal
 * optInAFInitLong:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor initial value for the Long direction
 * optInAFLong:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor for the Long direction
 * optInAFMaxLong:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor maximum value for the Long direction
 * optInAFInitShort:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor initial value for the Short direction
 * optInAFShort:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor for the Short direction
 * optInAFMaxShort:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor maximum value for the Short direction
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
/* optional input */
  double	 optInStartValue;
  double	 optInOffsetonReverse;
  double	 optInAFInitLong;
  double	 optInAFLong;
  double	 optInAFMaxLong;
  double	 optInAFInitShort;
  double	 optInAFShort;
  double	 optInAFMaxShort;
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
  if (nrhs < 2 || nrhs > 10) mexErrMsgTxt("#10 inputs possible #8 optional.");
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
  inSeriesRows = mxGetM(prhs[1]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInStartValue must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInStartValue =   mxGetScalar(prhs[2]);
  } else {
  	optInStartValue = 0.000000e+0;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInOffsetonReverse must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInOffsetonReverse =   mxGetScalar(prhs[3]);
  } else {
  	optInOffsetonReverse = 0.000000e+0;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInAFInitLong must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFInitLong =   mxGetScalar(prhs[4]);
  } else {
  	optInAFInitLong = 2.000000e-2;
  }
  if (nrhs >= 5+1) {
	if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1) 
    	mexErrMsgTxt("Input optInAFLong must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFLong =   mxGetScalar(prhs[5]);
  } else {
  	optInAFLong = 2.000000e-2;
  }
  if (nrhs >= 6+1) {
	if (!mxIsDouble(prhs[6]) || mxIsComplex(prhs[6]) ||
      mxGetN(prhs[6])*mxGetM(prhs[6]) != 1) 
    	mexErrMsgTxt("Input optInAFMaxLong must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFMaxLong =   mxGetScalar(prhs[6]);
  } else {
  	optInAFMaxLong = 2.000000e-1;
  }
  if (nrhs >= 7+1) {
	if (!mxIsDouble(prhs[7]) || mxIsComplex(prhs[7]) ||
      mxGetN(prhs[7])*mxGetM(prhs[7]) != 1) 
    	mexErrMsgTxt("Input optInAFInitShort must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFInitShort =   mxGetScalar(prhs[7]);
  } else {
  	optInAFInitShort = 2.000000e-2;
  }
  if (nrhs >= 8+1) {
	if (!mxIsDouble(prhs[8]) || mxIsComplex(prhs[8]) ||
      mxGetN(prhs[8])*mxGetM(prhs[8]) != 1) 
    	mexErrMsgTxt("Input optInAFShort must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFShort =   mxGetScalar(prhs[8]);
  } else {
  	optInAFShort = 2.000000e-2;
  }
  if (nrhs >= 9+1) {
	if (!mxIsDouble(prhs[9]) || mxIsComplex(prhs[9]) ||
      mxGetN(prhs[9])*mxGetM(prhs[9]) != 1) 
    	mexErrMsgTxt("Input optInAFMaxShort must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFMaxShort =   mxGetScalar(prhs[9]);
  } else {
  	optInAFMaxShort = 2.000000e-1;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_SAREXT(
                   startIdx, endIdx,
                   high,
                   low,
                   optInStartValue,
                   optInOffsetonReverse,
                   optInAFInitLong,
                   optInAFLong,
                   optInAFMaxLong,
                   optInAFInitShort,
                   optInAFShort,
                   optInAFMaxShort,
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
