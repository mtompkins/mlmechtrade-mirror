#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

 
/*
 * TA_HT_PHASOR - Hilbert Transform - Phasor Components
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 *
 * Output
 * -------------------
 * outInPhase
 * outQuadrature
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
  double*	 outInPhase;
  double*	 outQuadrature;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 1) mexErrMsgTxt("#1 inputs possible #0 optional.");
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

/* ----------------- OUTPUT ----------------- */
  outInPhase = mxCalloc(inSeriesRows, sizeof(double));
  outQuadrature = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_HT_PHASOR(
                   startIdx, endIdx,
                   inReal,
                   &outBegIdx, &outNbElement,
                   outInPhase,                   outQuadrature);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outInPhase);
   	   mxFree(outQuadrature);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outInPhase, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outInPhase);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outQuadrature, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outQuadrature);  
} /* END mexFunction */
