#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_MAMA - MESA Adaptive Moving Average
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInFastLimit:(From 1.000000e-2 to 9.900000e-1) Description: Upper limit use in the adaptive algorithm
 * optInSlowLimit:(From 1.000000e-2 to 9.900000e-1) Description: Lower limit use in the adaptive algorithm
 *
 * Output
 * -------------------
 * outMAMA
 * outFAMA
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
  double	 optInFastLimit;
  double	 optInSlowLimit;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outMAMA;
  double*	 outFAMA;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 3) mexErrMsgTxt("#3 inputs possible #2 optional.");
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
    	mexErrMsgTxt("Input optInFastLimit must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastLimit =   mxGetScalar(prhs[1]);
  } else {
  	optInFastLimit = 5.000000e-1;
  }
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInSlowLimit must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowLimit =   mxGetScalar(prhs[2]);
  } else {
  	optInSlowLimit = 5.000000e-2;
  }

/* ----------------- OUTPUT ----------------- */
  outMAMA = mxCalloc(inSeriesRows, sizeof(double));
  outFAMA = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_MAMA(
                   startIdx, endIdx,
                   inReal,
                   optInFastLimit,
                   optInSlowLimit,
                   &outBegIdx, &outNbElement,
                   outMAMA,                   outFAMA);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outMAMA);
   	   mxFree(outFAMA);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outMAMA, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outMAMA);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outFAMA, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outFAMA);  
} /* END mexFunction */
