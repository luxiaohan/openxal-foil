function [fitresult, gof] = createFit(xai, xiao)
%CREATEFIT(XAI,XIAO)
%  Create a fit.
%
%  Data for 'untitled fit 1' fit:
%      X Input : xai
%      Y Output: xiao
%  Output:
%      fitresult : a fit object representing the fit.
%      gof : structure with goodness-of fit info.
%
%  另请参阅 FIT, CFIT, SFIT.

%  由 MATLAB 于 28-Apr-2016 16:46:24 自动生成


%% Fit: 'untitled fit 1'.
[xData, yData] = prepareCurveData( xai, xiao );

% Set up fittype and options.
ft = fittype( 'fourier1' );
opts = fitoptions( 'Method', 'NonlinearLeastSquares' );
opts.Display = 'Off';
opts.Lower = [-Inf -Inf -Inf 0.85];
opts.StartPoint = [0 0 0 0.698131700797732];
opts.Upper = [Inf Inf Inf 0.88];

% Fit model to data.
[fitresult, gof] = fit( xData, yData, ft, opts );

% % Plot fit with data.
% figure( 'Name', 'untitled fit 1' );
% h = plot( fitresult, xData, yData );
% legend( h, 'xiao vs. xai', 'untitled fit 1', 'Location', 'NorthEast' );
% % Label axes
% xlabel( 'xai' );
% ylabel( 'xiao' );
% grid on


