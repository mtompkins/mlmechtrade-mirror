#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_BETA - Beta
 * Input Parameters
 * -------------------
 * inReal0
 * inReal1
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 1 to 100000) Description: Number of period
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
  double * inReal0;
  double * inReal1;
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
  if (nrhs < 2 || nrhs > 3) mexErrMsgTxt("#3 inputs possible #1 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix inReal0. */
  inReal0 = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input inReal0. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("inReal0 only vector alowed.");
  /* Create a pointer to the input matrix inReal1. */
  inReal1 = mxGetPr(prhs[1]);
  /* Get the dimensions of the matrix input inReal1. */
  inSeriesCols = mxGetN(prhs[1]);
  if (inSeriesCols != 1) mexErrMsgTxt("inReal1 only vector alowed.");
  inSeriesRows = mxGetM(prhs[1]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInTimePeriod = (int)  mxGetScalar(prhs[2]);
  } else {
  	optInTimePeriod = 5;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_BETA(
                   startIdx, endIdx,
                   inReal0,
                   inReal1,
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
