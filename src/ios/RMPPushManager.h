#import <Cordova/CDV.h>
#import "RMPHelper.h"

@interface RMPPushManager : RMPHelper

@property (nonatomic, strong) NSDictionary *notificationMessage;
- (void)registerForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;
- (void)notificationReceived;

- (void)promoCodeFromNotification:(CDVInvokedUrlCommand *)command;
- (void)initPush:(CDVInvokedUrlCommand *)command;

@end
