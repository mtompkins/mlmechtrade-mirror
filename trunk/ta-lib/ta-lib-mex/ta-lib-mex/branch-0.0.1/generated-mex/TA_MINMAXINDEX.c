#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_MINMAXINDEX - Indexes of lowest and highest values over a specified period
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
 * outMinIdx
 * outMaxIdx
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
  int*	 outMinIdx;
  int*	 outMaxIdx;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 2) mexErrMsgTxt("#2 inputs possible #1 optional.");
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
    	mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInTimePeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInTimePeriod = 30;
  }

/* ----------------- OUTPUT ----------------- */
  outMinIdx = mxCalloc(inSeriesRows, sizeof(int));
  outMaxIdx = mxCalloc(inSeriesRows, sizeof(int));
/* -------------- Invocation ---------------- */

	retCode = TA_MINMAXINDEX(
                   startIdx, endIdx,
                   inReal,
                   optInTimePeriod,
                   &outBegIdx, &outNbElement,
                   outMinIdx,                   outMaxIdx);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outMinIdx);
   	   mxFree(outMaxIdx);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateNumericMatrix(outBegIdx+outNbElement,1, mxINT32_CLASS, mxREAL);
  memcpy(((int *) mxGetData(plhs[0]))+outBegIdx, outMinIdx, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outMinIdx);  
  plhs[1] = mxCreateNumericMatrix(outBegIdx+outNbElement,1, mxINT32_CLASS, mxREAL);
  memcpy(((int *) mxGetData(plhs[1]))+outBegIdx, outMaxIdx, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outMaxIdx);  
} /* END mexFunction */
