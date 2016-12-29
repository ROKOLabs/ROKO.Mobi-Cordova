//
//  ROKOPromoCampaignInfo+ROKOPromoCampaignInfoMapper.h
//  HelloRoko
//
//  Created by Maslov Sergey on 30.05.16.
//
//

#import "ROKOPromoCampaignInfo+ROKOPromoCampaignInfoMapper.h"
#import <Foundation/NSFormatter.h>

@implementation ROKOPromoCampaignInfo (ROKOPromoCampaignInfoMapper)

+ (EKObjectMapping *)objectMapping {
  return [EKObjectMapping mappingForClass:self withBlock:^(EKObjectMapping *mapping) {
      [mapping mapPropertiesFromArray:@[@"promoCode", @"active", @"refInfo"]];
  }];
}

@end
