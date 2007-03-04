#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

 
/*
 * TA_HT_TRENDMODE - Hilbert Transform - Trend vs Cycle Mode
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 *
 * Output
 * -------------------
 * outInteger
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
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  int*	 outInteger;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 1) mexErrMsgTxt("#1 inputs possible #0 optional.");
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

/* ----------------- OUTPUT ----------------- */
  outInteger = mxCalloc(inSeriesRows, sizeof(int));
/* -------------- Invocation ---------------- */

	retCode = TA_HT_TRENDMODE(
                   startIdx, endIdx,
                   inReal,
                   &outBegIdx, &outNbElement,
                   outInteger);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outInteger);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateNumericMatrix(outBegIdx+outNbElement,1, mxINT32_CLASS, mxREAL);
  memcpy(((int *) mxGetData(plhs[0]))+outBegIdx, outInteger, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outInteger);  
} /* END mexFunction */
