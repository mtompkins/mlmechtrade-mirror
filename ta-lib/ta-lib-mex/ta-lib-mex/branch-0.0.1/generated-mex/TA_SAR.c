#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_SAR - Parabolic SAR
 * Input Parameters
 * -------------------
 * high
 * low
 * 
 * Optional Parameters
 * -------------------
 * optInAccelerationFactor:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor used up to the Maximum value
 * optInAFMaximum:(From 0.000000e+0 to 3.000000e+37) Description: Acceleration Factor Maximum value
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
  double	 optInAccelerationFactor;
  double	 optInAFMaximum;
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
  if (nrhs < 2 || nrhs > 4) mexErrMsgTxt("#4 inputs possible #2 optional.");
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
    	mexErrMsgTxt("Input optInAccelerationFactor must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAccelerationFactor =   mxGetScalar(prhs[2]);
  } else {
  	optInAccelerationFactor = 2.000000e-2;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInAFMaximum must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInAFMaximum =   mxGetScalar(prhs[3]);
  } else {
  	optInAFMaximum = 2.000000e-1;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_SAR(
                   startIdx, endIdx,
                   high,
                   low,
                   optInAccelerationFactor,
                   optInAFMaximum,
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
