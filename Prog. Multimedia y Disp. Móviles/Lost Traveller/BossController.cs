using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BossController : MonoBehaviour
{
    public Transform target;
    private float speed = 6.8f;
    private Vector3 start, end;
    private SpriteRenderer spriteRenderer;

    void Start()
    {
        start = transform.position;
        end = target.position;
        spriteRenderer = GetComponent<SpriteRenderer>();
    }

    // Update is called once per frame
    void Update()
    {
        transform.position = Vector3.MoveTowards(transform.position, target.position, speed * Time.deltaTime);

        if (transform.position == target.position)
        {

            if (target.position == end)
            {
                target.position = start;
                spriteRenderer.flipX = false;
            }
            else
            {
                target.position = end;
                spriteRenderer.flipX = true;

            }
        }
    }
}
