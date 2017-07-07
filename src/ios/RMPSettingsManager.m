//
//  RMPSettingsManager.m
//
//  Created by Alexander Zakatnov on 07.06.17.
//  Copyright © 2017 ROKO Labs. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RMPSettingsManager.h"
#import <ROKOMobi/ROKOMobi.h>

@implementation RMPSettingsManager
    
    - (void)getSDKInfo:(CDVInvokedUrlCommand *)command {
        NSString * token = [ROKOComponentManager sharedManager].apiToken;
        NSString * baseURL = [ROKOComponentManager sharedManager].baseURL;
        
        NSDictionary * infoDict = [[NSBundle mainBundle] infoDictionary];
        NSString * version = [infoDict objectForKey:@"CFBundleShortVersionString"];
        NSString * buildNumber = [infoDict objectForKey:@"CFBundleVersion"];
        NSString * appVersion = [NSString stringWithFormat:@"%@ (%@)", version, buildNumber];
        
        ROKOPortalManager *portal = [[ROKOComponentManager sharedManager] portalManager];
        [portal getPortalInfoWithCompletionBlock:^(ROKOPortalInfo * _Nullable info, NSError * _Nullable error) {
            NSDictionary * result = @{@"apiVersion": info.version,
                                      @"applicaitonName": info.applicationName,
                                      @"mobileAppVersion": appVersion,
                                      @"apiURL": baseURL,
                                      @"apiToken": token};
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
        }];
        
    }
    
    - (void)changeEnvironment:(CDVInvokedUrlCommand *)command {
        
    }

@end
