#import <UIKit/UIKit.h>
#import "RMPInstabotManager.h"
#import <ROKOMobi/ROKOMobi.h>

@implementation RMPInstabotManager

- (void)pluginInitialize {
    [super pluginInitialize];
}

- (void)loadConversation:(CDVInvokedUrlCommand *)command {
    [self parseCommand:command];
    
    NSNumber *conversationId = command.arguments[0];
    if (conversationId) {
        __weak __typeof__(self) weakSelf = self;
        ROKOInstaBot *bot = [[ROKOInstaBot alloc] init];
        [bot loadConversationWithId: [conversationId integerValue] completionBlock:^(ROKOInstaBotViewController * _Nullable controller, NSError * _Nullable error) {
            if (error) {
                [weakSelf handleError:error];
            } else {
                dispatch_async(dispatch_get_main_queue(), ^{
                    UIWindow *keyWindow = [[UIApplication sharedApplication] keyWindow];
                    UIViewController *rootViewController = keyWindow.rootViewController;
                    [rootViewController presentViewController:controller animated:YES completion:nil];
                });
                
                CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Run bot dialog Successful"];
                [weakSelf.commandDelegate sendPluginResult:result callbackId:weakSelf.command.callbackId];
                
            }
        }];
    }
}

@end
