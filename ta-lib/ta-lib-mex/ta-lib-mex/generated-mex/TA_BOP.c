#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

 
/*
 * TA_BOP - Balance Of Power
 * Input Parameters
 * -------------------
 * open
 * high
 * low
 * close
 * 
 * Optional Parameters
 * -------------------
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
  double * open;
  double * high;
  double * low;
  double * close;
/* optional input */
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
  if (nrhs < 4 || nrhs > 4) mexErrMsgTxt("#4 inputs possible #0 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix open. */
  open = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input open. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("open only vector alowed.");
  /* Create a pointer to the input matrix high. */
  high = mxGetPr(prhs[1]);
  /* Get the dimensions of the matrix input high. */
  inSeriesCols = mxGetN(prhs[1]);
  if (inSeriesCols != 1) mexErrMsgTxt("high only vector alowed.");
  /* Create a pointer to the input matrix low. */
  low = mxGetPr(prhs[2]);
  /* Get the dimensions of the matrix input low. */
  inSeriesCols = mxGetN(prhs[2]);
  if (inSeriesCols != 1) mexErrMsgTxt("low only vector alowed.");
  /* Create a pointer to the input matrix close. */
  close = mxGetPr(prhs[3]);
  /* Get the dimensions of the matrix input close. */
  inSeriesCols = mxGetN(prhs[3]);
  if (inSeriesCols != 1) mexErrMsgTxt("close only vector alowed.");
  inSeriesRows = mxGetM(prhs[3]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_BOP(
                   startIdx, endIdx,
                   open,
                   high,
                   low,
                   close,
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
