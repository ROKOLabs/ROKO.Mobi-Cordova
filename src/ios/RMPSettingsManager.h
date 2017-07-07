#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface RMPSettingsManager: CDVPlugin

    - (void)getSDKInfo:(CDVInvokedUrlCommand *)command;
    - (void)changeEnvironment:(CDVInvokedUrlCommand *)command;

@end
