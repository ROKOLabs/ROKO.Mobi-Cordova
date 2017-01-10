//
//  ROKOUserIcon+ROKOUserIconMapper.h
//  HelloRoko
//
//  Created by Maslov Sergey on 30.05.16.
//
//

#import "ROKOUserIcon+ROKOUserIconMapper.h"
#import <Foundation/NSFormatter.h>

@implementation ROKOUserIcon (ROKOUserIconMapper)

+ (EKObjectMapping *)objectMapping {
  return [EKObjectMapping mappingForClass:self withBlock:^(EKObjectMapping *mapping) {
      [mapping mapPropertiesFromArray:@[@"urlString", @"urlExpirationDate"]];
  }];
}

@end
