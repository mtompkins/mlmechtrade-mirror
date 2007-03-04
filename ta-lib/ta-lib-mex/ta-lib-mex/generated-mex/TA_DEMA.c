#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_DEMA - Double Exponential Moving Average
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 2 to 100000) Description: Number of period
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
  double * inReal;
/* optional input */
  int	 optInTimePeriod;
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
  if (nrhs < 1 || nrhs > 2) mexErrMsgTxt("#2 inputs possible #1 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
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
  	optInTimePeriod = 30;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_DEMA(
                   startIdx, endIdx,
                   inReal,
                   optInTimePeriod,
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
