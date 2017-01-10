//
//  ROKOReferralProgram+ROKOReferralProgramMapper.h
//  HelloRoko
//
//  Created by Maslov Sergey on 30.05.16.
//
//

#import "ROKOReferralProgram+ROKOReferralProgramMapper.h"
#import <Foundation/NSFormatter.h>

@implementation ROKOReferralProgram(ROKOReferralProgramMapper)

+ (EKObjectMapping *)objectMapping {
  return [EKObjectMapping mappingForClass:self withBlock:^(EKObjectMapping *mapping) {
      [mapping mapPropertiesFromArray:@[@"errorMessage"]];
      [mapping hasOne:[ROKOReferralProgramDefinition class] forKeyPath:@"info"];
      [mapping hasOne:[ROKOReferralDiscountItem class] forKeyPath:@"discount"];
      [mapping hasMany:[ROKOReferralDiscountItem class] forKeyPath:@"rewards"];
  }];
}

@end
