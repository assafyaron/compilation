#!/bin/bash
setenv UDOCKER_DIR /vol/csrepo
udocker run --bindhome compiler_course
cd Compilation/ex1
make
