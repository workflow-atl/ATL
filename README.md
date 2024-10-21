# Formal Verification of Business Constraints in Workflow-Based Applications
## Authors: Florin Stoica and Laura Florentina Stoica 
This is the code repository for the paper _Formal Verification of Business Constraints in Workflow-Based Applications_.  
We introduce a method for verifying the correctness properties of workflow processes by utilizing our database-embedded Alternating-time Temporal Logic (ATL) model checker. First, the work-flow specification is translated into a Concurrent Game Structure (CGS). Next, the desired property is expressed as an ATL formula. Finally, the ATL model checker is executed to verify whether the correctness properties hold for the model. To support users in the formalization of business con-straints, the proposed solution implements an assistant based on generative AI. The experimental results show that employing the chain-of-thought prompting method significantly enhances the reasoning process performance when using the GPT-4o model.
Contains practical approaches for embedding the ATL model checker into the Oracle database 
## Notes on Environment Setup
- The **java** directory contains the Java code of the ATL model checker.
- Software package versions: Python 3.8.X, Auto-sklearn 0.14.3, H2O cluster version 3.36.0.1
