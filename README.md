# Alert Hook

[![Build Status][1]][2]

Alert Hook is a tool for getting your alerts from alerting services and send them to other places like messengers or etc.

## Supported Services

* **AlertManager** to **BaleMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/balemessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
              balemessenger {
                api-token: "alerthook"
                bot-token: "alerthook"
                chat-id: 12345
            
                template: """
                  *Status:* {{status}}
                  *Starts At:* {{startsAt}}
                  *Ends At:* {{endsAt}}
                  *Generator URL:* {{generatorURL}}
                  *Alert Name:* {{alertname}}
                """
              }
            }
        ```

[1]: https://travis-ci.org/rashadansari/alert-hook.svg?branch=master
[2]: https://travis-ci.org/rashadansari/alert-hook