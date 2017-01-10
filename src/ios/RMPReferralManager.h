#import <Cordova/CDV.h>
#import "RMPHelper.h"

@interface RMPReferralManager : RMPHelper

- (void)loadReferralDiscountsList:(CDVInvokedUrlCommand *)command;

- (void)markReferralDiscountAsUsed:(CDVInvokedUrlCommand *)command;

- (void)loadDiscountInfoWithCode:(CDVInvokedUrlCommand *)command;

- (void)activateDiscountWithCode:(CDVInvokedUrlCommand *)command;

- (void)completeDiscountWithCode:(CDVInvokedUrlCommand *)command;

- (void)inviteFriends:(CDVInvokedUrlCommand *)command;

- (void)loadInfo:(CDVInvokedUrlCommand *)command;

- (void)redeemReferralDiscount:(CDVInvokedUrlCommand *)command;

- (void)issueReward:(CDVInvokedUrlCommand *)command;

- (void)loadReferralRewardList:(CDVInvokedUrlCommand *)command;

- (void)redeemReferralReward:(CDVInvokedUrlCommand *)command;

@end
