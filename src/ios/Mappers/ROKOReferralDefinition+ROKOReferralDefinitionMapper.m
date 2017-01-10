//
//  ROKOReferralDefinition+ROKOReferralDefinitionMapper.h
//  HelloRoko
//
//  Created by Maslov Sergey on 30.05.16.
//
//

#import "ROKOReferralDefinition+ROKOReferralDefinitionMapper.h"
#import <Foundation/NSFormatter.h>

@implementation ROKOReferralDefinition (ROKOReferralDefinitionmMapper)

+ (EKObjectMapping *)objectMapping {
  return [EKObjectMapping mappingForClass:self withBlock:^(EKObjectMapping *mapping) {
      [mapping mapPropertiesFromArray:@[@"value", @"enabled", @"canBeUsed"]];

        NSDictionary *discountTypes = @{ @"ROKODiscountTypeUnspecified": @(ROKODiscountTypeUnspecified),
                                   @"ROKODiscountTypePercent": @(ROKODiscountTypePercent),
                                   @"ROKODiscountTypeFixed": @(ROKODiscountTypeFixed),
                                   @"ROKODiscountTypeFree": @(ROKODiscountTypeFree),
                                   @"ROKODiscountTypeMatching": @(ROKODiscountTypeMatching)};
        
        [mapping mapKeyPath:@"type" toProperty:@"type" withValueBlock:^(NSString *key, id value) {
            return discountTypes[value];
        } reverseBlock:^id(id value) {
            return [discountTypes allKeysForObject:value].firstObject;
        }];

  }];
}

@end
