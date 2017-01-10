//
//  ROKOReferralProgramDefinition+ROKOReferralProgramDefinitionMapper.h
//  HelloRoko
//
//  Created by Maslov Sergey on 30.05.16.
//
//

#import "ROKOReferralProgramDefinition+ROKOReferralProgramDefinitionMapper.h"
#import <Foundation/NSFormatter.h>

@implementation ROKOReferralProgramDefinition (ROKOReferralProgramDefinitionMapper)

+ (EKObjectMapping *)objectMapping {
  return [EKObjectMapping mappingForClass:self withBlock:^(EKObjectMapping *mapping) {
      [mapping mapPropertiesFromArray:@[@"name"]];
      [mapping hasOne:[ROKOReferralDefinition class] forKeyPath:@"discount"];
      [mapping hasOne:[ROKOReferralDefinition class] forKeyPath:@"reward"];
  }];
}

@end
