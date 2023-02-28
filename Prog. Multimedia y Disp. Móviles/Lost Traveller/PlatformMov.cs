using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlatformMov : MonoBehaviour
{

    public Transform targetPlatform;
    private float speed = 3f;
    private Vector3 start, end;



    void Start()
    {
        start = transform.position;
        end = targetPlatform.position;
    }

    
    void Update()
    {
        transform.position = Vector3.MoveTowards(transform.position, targetPlatform.position, speed * Time.deltaTime);

        if(transform.position == targetPlatform.position)
        {

            if (targetPlatform.position == end)
            {
                targetPlatform.position = start;
            }
            else
            {
                targetPlatform.position = end;
            }
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        collision.transform.parent = transform;
    }

    private void OnCollisionExit2D(Collision2D collision)
    {
        collision.transform.parent = null;
    }
}
