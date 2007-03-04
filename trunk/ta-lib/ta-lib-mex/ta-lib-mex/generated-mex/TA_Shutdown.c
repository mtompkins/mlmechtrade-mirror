#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
   TA_Shutdown();
} /* END mexFunction */